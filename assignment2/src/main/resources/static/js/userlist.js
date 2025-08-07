// 전역 변수
let layout;
let signupButtonForm;
let filterForm;
let userlistGrid;
let pagination;
const pageSize = 10;
let currentParams = "";
let temporaryDataStore;

// 레이아웃 생성
function createLayout(rootId = "userlistform") {
    layout = new dhx.Layout(document.getElementById(rootId), {
        rows: [
            {
                id: "signupbuttoncontent",
                height: "content",
            },
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
// 회원가입 버튼 생성
function createSignupButtonForm() {
    signupButtonForm = new dhx.Form(null, {
        rows: [
            {
                type: "button",
                text: "회원가입",
                name: "signup",
                css: "signupbtn"
            }
        ]
    })
    layout.getCell("signupbuttoncontent").attach(signupButtonForm);

    signupButtonForm.events.on("click", () => {
        window.location.href = `/api/signup`;
    })
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
                    data: ["A","B","C","D","E","F"].map(v => ({
                                                            id: v,
                                                            value: v
                                                    }))
                },
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
        console.log("currentParams", currentParams);
        loadUserList(0, currentParams);
    });

    // 전체 조회 버튼
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
                    text:"ID",
                    align: "center",
                }],
                align: "center",
                width:150
            },
            {
                id:"name",
                header:[{
                    text:"이름",
                    align: "center",
                }],
                align: "center",
                width:150
            },
            {
                id:"level",
                header:[{
                    text:"레벨",
                    align: "center",
                }],
                align: "center",
                width:150
            },
            {
                id:"desc",
                header:[{
                    text:"설명",
                    align: "center",
                }],
                align: "center",
                width:300
            },
            {
                id:"regDate",
                header:[{
                    text:"가입일",
                    align: "center",
                }],
                align: "center",
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
                  width:300
            },
            {
                id: "edit",
                header: [{
                    text: "수정",
                    align: "center",
                }],
                align: "center",
                htmlEnable : true,
                width: 80,
                height: 30,
                template: function(value, row, col) {
                    return `<button
                        class="edit-user-btn"
                        data-id="${row.id}">수정</button>`;
                }
            },
        ],
        autoWidth:true,
        resizable:true,
        height:442,
        virtual: false,
        data:[]
    });
    layout.getCell("userlistcontent").attach(userlistGrid);
}

function createPagination() {
    if (pagination) return;

   // temporaryDataStore 생성 및 getLength 설정
    temporaryDataStore = new dhx.DataCollection();
    temporaryDataStore.totalCount = 0;
    temporaryDataStore.getLength = function() {
       // totalCount가 바뀔 때마다 동적으로 읽어줌
       return (typeof this.totalCount === "number")
           ? this.totalCount
           : 0;
    };
    pagination = new dhx.Pagination(null, {
        css: "dhx_widget--bordered dhx_widget--no-border_top",
        data: temporaryDataStore,
        pageSize: pageSize
    });
    layout.getCell("paginationcontent").attach(pagination);

    pagination.events.on("change", newPage => {
        loadUserList(newPage, currentParams);
    });
}

// 데이터 로딩 함수
function loadUserList(page = 0, params = "") {
    const url = `/user/list/${page}` + (params ? `?${params}` : "");

    fetch(url)
        .then(async res => {
            if (!res.ok) {
                // 실패시 에러메시지 파싱
                const error = await res.json();
                console.log("error", error)
//                dhx.alert({ title: "오류", text: error.message || "에러" });
                dhx.alert({ title: "오류", text: error.errors[0].defaultMessage || "에러" });
                return null;
            }
            return res.json();
        })
        .then(data => {
         if (!data) return;
            userlistGrid.data.removeAll();
            userlistGrid.data.parse(data.content);
            temporaryDataStore.totalCount = data.totalElements;

            pagination.setPage(data.number);

            console.log("data", data)
            console.log("data.content", data.content);
            console.log("userlistGrid.data",userlistGrid.data)
            console.log("pagination.config", pagination.config)
            console.log("userlistGrid.data.serialize()", userlistGrid.data.serialize());
            console.log("userlistGrid.data.getLength()", userlistGrid.data.getLength());
        })
        .catch(err => {
            dhx.alert({ title:"오류", text:"유저리스트 로딩에 실패했습니다." });
        });
}

document.addEventListener("click", function(e) {
    if (e.target.classList.contains("edit-user-btn")) {
        const userId = e.target.dataset.id; // 실제 유저의 id 값
        console.log(userId)
        // edituserinfo 페이지(또는 SPA 라우트)로 id만 넘기기
        window.location.href = `/api/edit/userinfo?id=${userId}`;
        // 만약 SPA hash 방식이면 location.hash = "#edituserinfo?id=" + userId;
        }
});



// 초기화
function init() {
    createLayout();
    createSignupButtonForm();
    createFilterForm();
    createUserlistGrid();
    createPagination();
}

document.addEventListener("DOMContentLoaded", init);
