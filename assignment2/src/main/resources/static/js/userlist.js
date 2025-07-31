// 1. 전역 변수 선언

let layout;
let menuForm;
let filterForm;
let userlistGrid;
let pagination;
let pageSize = 10;
let url;

// 유저 리스트 로딩 함수

function loadUserList(page = 0, params = "") {
    url = `/user/list/${page}` + (params ? `?${params}` : "");
    fetch(url)
        .then(res => {
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            return res.json();
        })
        .then(data => {
          // 1) 슬라이스된 데이터만 parse
          userlistGrid.data.parse(data.content);

          // 2) 서버 전체 row 수 저장
          userlistGrid.data.totalCount = data.totalElements;

          // 3) getLength()를 totalCount 리턴하도록 덮어쓰기
          userlistGrid.data.getLength = function(){
            return this.totalCount;
          };

          console.log("userlistGrid.data", userlistGrid.data);

          // 4) 페이징 만들고 현재 페이지 세팅
          createPagination(userlistGrid.data);
          pagination.setPage(data.number);
//         pagination.setPage(data.number, true);
        })
        .catch(err => {
            console.error("조회 오류:", err);
            alert("유저리스트 로딩에 실패했습니다.");
        });
}

// 1) 현재 폼 값을 그대로 params로
function getSearchParams() {
    return new URLSearchParams(filterForm.getValue()).toString();
}

// 2) 첫 페이지 이동
function goFirstPage() {
    loadUserList(0, getSearchParams());
}

// 3) 이전 페이지 이동
function goPrevPage() {
    const cur = pagination.getPage();
    if (cur > 0) {
        loadUserList(cur - 1, getSearchParams());
    }
}

// 4) 다음 페이지 이동
function goNextPage() {
    const cur  = pagination.getPage();
    const last = Math.ceil(userlistGrid.data.totalCount / pageSize) - 1;
    if (cur < last) {
        loadUserList(cur + 1, getSearchParams());
    }
}

// 5) 마지막 페이지 이동
function goLastPage() {
    const last = Math.ceil(userlistGrid.data.totalCount / pageSize) - 1;
    loadUserList(last, getSearchParams());
}

const init = () => {

    createLayout();
    createFilterForm();
    createUserlistGrid();

}

// 2. 레이아웃 생성
function createLayout(rootId = "userlistform") {
    const root = document.getElementById(rootId);
    layout = new dhx.Layout(root, {
        rows: [
            {
                id: "header",
                height: 60
            },
            {
                id: "body", cols: [
                    {
                        id: "content",
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
                    }
                ]
            },
            { id: "footer", height: 40 }
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
                    data: [
                        {
                            id: "A",
                            value: "A"
                        },
                        {
                            id: "B",
                            value: "B"
                        },
                        {
                            id: "C",
                            value: "C"
                        },
                        {
                            id: "D",
                            value: "D"
                        },
                        {
                            id: "E",
                            value: "E"
                        },
                        {
                            id: "F",
                            value: "F"
                        }
                   ]
                },
                {
                    type: "input",
                    name: "desc",
                    placeholder: "설명",
                    width: 100
                },
                {
                    type: "datepicker",
                    name: "startRegDate",
                    placeholder: "시작일",
                    dateFormat: "%Y-%m-%d",
                    width: 120
                },
                {
                    type: "datepicker",
                    name: "endRegDate",
                    placeholder: "종료일",
                    dateFormat: "%Y-%m-%d",
                    width: 120
                },
                {
                    type: "button",
                    name: "search",
                    id: "search",
                    text: "조회",
                    color: "primary",
                    size: "medium"
                },
                {
                    type: "button",
                    name: "reset",
                    text: "초기화",
                    color: "secondary",
                    size: "medium"
                }
            ]
        }]
    });
    layout.getCell("filtercontent").attach(filterForm);

    filterForm.getItem("search").events.on("click", () => {
        const params = new URLSearchParams(filterForm.getValue()).toString();
        loadUserList(0, params);
    });
    filterForm.getItem("reset").events.on("click", () => {
        filterForm.clear();
        loadUserList(0, "");
    });
}


// 유저리스트 grid 생성

function createUserlistGrid() {
    userlistGrid = new dhx.Grid(null, {
        columns: [
            { id: "id",     header: [{ text: "ID" }] },
            { id: "name",   header: [{ text: "이름" }] },
            { id: "level",  header: [{ text: "레벨" }] },
            { id: "desc",   header: [{ text: "설명" }] },
            { id: "regDate", header: [{ text: "가입일" }] }
        ],
        css: "userlistgrid",
        autoWidth: true,
        resizable: true,
        height: 442,
        data: []
    });
    layout.getCell("userlistcontent").attach(userlistGrid);
}


// 페이징 요소 생성

function createPagination(dataStore) {
    if (pagination) {
        pagination.destructor();
    }
    pagination = new dhx.Pagination(null, {
        css: "dhx_widget--bordered dhx_widget--no-border_top",
        data: dataStore,
        pageSize
    });
    layout.getCell("paginationcontent").attach(pagination);
    pagination.events.on("change", (newPage) => {
        loadUserList(newPage, new URLSearchParams(filterForm.getValue()).toString());
    });
}

document.addEventListener("DOMContentLoaded", init);