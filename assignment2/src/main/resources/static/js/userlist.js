// 전역 변수
let layout;
let filterForm;
let userlistGrid;
let pagination;
const pageSize = 10;
let currentParams = "";
let ignorePageEvent = false;
let fakeDataStore;

// 레이아웃 생성
function createLayout(rootId = "userlistform") {
    layout = new dhx.Layout(document.getElementById(rootId), {
        rows: [
            {
                id: "filtercontent",
                height: "content"
            },
            {
                id: "userlistcontent",
                height: "*"
            },
            {
                id: "paginationcontent",
                height: 60
            }
        ]
    });
}

// 필터 폼 생성
function createFilterForm() {
    filterForm = new dhx.Form(null, {
        rows: [{
            cols: [
                {
                    type: "input",
                    name: "id",
                    placeholder: "ID",
                    width: 80
                },
                {
                    type: "input",
                    name: "name",
                    placeholder: "이름",
                    width: 80
                },
                {
                    type: "combo",
                    name: "level",
                    placeholder: "레벨",
                    width: 80,
                    data: ["A","B","C","D","E","F"].map(v => ({ id: v, value: v })) },
                {
                    type: "input",
                    name: "desc",
                    placeholder: "설명",
                    width:100
                },
                {
                    type: "datepicker",
                    name: "startRegDate",
                    placeholder: "시작일",
                    dateFormat: "%Y-%m-%d",
                    width:120
                },
                {
                    type: "datepicker",
                    name: "endRegDate",
                    placeholder: "종료일",
                    dateFormat: "%Y-%m-%d",
                    width:120
                },
                {
                    type: "button",
                    name: "search",
                    id:"search",
                    text:"조회",
                    color:"primary",
                    size:"medium"
                },
                {
                    type: "button",
                    name: "allusersearch",
                    text:"전체검색",
                    color:"secondary",
                    size:"medium"
                }
            ]
        }]
    });
    layout.getCell("filtercontent").attach(filterForm);

  // 조회 버튼
    filterForm.getItem("search").events.on("click", () => {
        currentParams = new URLSearchParams(filterForm.getValue()).toString();
        loadUserList(0, currentParams);
    });
    // 초기화 버튼
    filterForm.getItem("allusersearch").events.on("click", () => {
        filterForm.clear();
        currentParams = "";
        loadUserList(0, "");
    });
}

// 그리드 생성
function createUserlistGrid() {
    userlistGrid = new dhx.Grid(null, {
        columns: [
            {
                id:"id",
                header:[{
                    text:"ID"
                }],
                width:200
            },
            {
                id:"name",
                header:[{
                    text:"이름"
                }],
                width:200
            },
            {
                id:"level",
                header:[{
                    text:"레벨"
                }],
                width:200
            },
            {
                id:"desc",
                header:[{
                    text:"설명"
                }],
                width:350
            },
            {
                id:"regDate",
                header:[{
                    text:"가입일"
                }],
                template: function(value) {
                    const date = new Date(value);
                    return date.toLocaleString("ko-KR", {
                      year: 'numeric',
                      month: 'long',
                      day: 'numeric',
                      hour: '2-digit',
                      minute: '2-digit'
                    }).replace(/\./g, '년')
                      .replace('월', '월 ')
                      .replace('일', '일 ')
                      .replace(':', '시 ') + '분';
                  },
                  width:350
             }
        ],
        autoWidth:true,
        resizable:true,
        height:442,
        virtual: false,
        data:[]
    });
    layout.getCell("userlistcontent").attach(userlistGrid);

  // getLength: 최초 1회만 정의
    if (!userlistGrid.data.getLengthPatched) {
        userlistGrid.data.getLength = function() {
            return (typeof this.totalCount === "number" && !isNaN(this.totalCount))
                ? this.totalCount
                : 0;
        };
    userlistGrid.data.getLengthPatched = true;
    }
}

function createPagination() {
    if (pagination) return;

   // 1) fakeDataStore 생성 및 getLength 패치
       fakeDataStore = new dhx.DataCollection();
       fakeDataStore.totalCount = 0;
       fakeDataStore.getLength = function() {
           // totalCount 가 바뀔 때마다 동적으로 읽어줌
           return (typeof this.totalCount === "number" && !isNaN(this.totalCount))
               ? this.totalCount
               : 0;
       };
        pagination = new dhx.Pagination(null, {
            css: "dhx_widget--bordered dhx_widget--no-border_top",
            data: fakeDataStore,
            pageSize: pageSize
        });
    layout.getCell("paginationcontent").attach(pagination);

    pagination.events.on("change", newPage => {
        if (ignorePageEvent) return;
    loadUserList(newPage, currentParams);
    });
}

// 데이터 로딩 함수
function loadUserList(page = 0, params = "") {
    const url = `/user/list/${page}` + (params ? `?${params}` : "");

    fetch(url)
        .then(res => {
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            return res.json();
        })
        .then(data => {
//            userlistGrid.data.totalCount = data.totalElements;
            userlistGrid.data.removeAll();
            userlistGrid.data.parse(data.content);
            userlistGrid.paint();

//        const totalPages = Math.ceil(data.totalElements / pageSize);
//        pagination.config.pagesCount = totalPages;
        userlistGrid.data.totalCount = data.totalElements;
        fakeDataStore.totalCount = data.totalElements;
        pagination.paint(); // 갱신

      // 페이지 표시
            ignorePageEvent = true;
            pagination.setPage(data.number, true);
            ignorePageEvent = false;

            console.log("data", data);
            console.log("data.content", data.content);
            console.log("userlistGrid.data",userlistGrid.data)
            console.log("pagination.config", pagination.config)
            console.log("userlistGrid.data.serialize()", userlistGrid.data.serialize());
            console.log("userlistGrid.data.getLength()", userlistGrid.data.getLength());
            console.log("dom 업데이트 확인 : ", document.querySelectorAll('.dhx_grid-row'))
        })
        .catch(err => {
            dhx.alert({ title:"오류", text:"유저리스트 로딩에 실패했습니다." });
        });
}

// 최초 1회만 createUserlistGrid(), createPagination() 호출!
// loadUserList에서 그리드 인스턴스 초기화하면 안 됨!
// 페이징 이벤트, 데이터 parse/removeAll 순서 점검!

// 초기화
function init() {
    createLayout();
    createFilterForm();
    createUserlistGrid();
    createPagination(userlistGrid.data); // 반드시 최초 1번만!
}

document.addEventListener("DOMContentLoaded", init);
