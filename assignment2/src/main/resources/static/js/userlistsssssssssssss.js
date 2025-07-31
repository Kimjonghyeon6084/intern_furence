let pageSize = 10;

const root = document.getElementById("userlistform");

const layout = new dhx.Layout(root, {
    rows: [
        {
            id: "header",
            height: 60
        },
        {
            id: "body",
            cols: [
                {
                    id: "menu",
                    width: 150
                },
                {
                    id: "content",
                    rows: [
                        {
                            id: "filtercontent"
                        },
                        {
                            id: "userlistcontent"
                        },
                        {
                            id: "paginationcontent"
                        }
                    ]
                }
            ]
        },
        {
            id: "footer",
            height: 40
        }
    ]
});

const menu = new dhx.Form(null, {
    css: "dhx_widget--bordered dhx_widget--bg_white",
    rows: [
        {
            type: "button",
            id: "fileupload",
            text: "파일업로드"
        },
        {
            type: "button",
            id: "userlist",
            text: "유저리스트"
        }
    ]
});

const filterForm = new dhx.Form(null, {
    rows: [
        {
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
                            id: "1",
                            value: "A"
                        },
                        {
                            id: "2",
                            value: "B"
                        },
                        {
                            id: "3",
                            value: "C"
                        },
                        {
                            id: "4",
                            value: "D"
                        },
                        {
                            id: "5",
                            value: "E"
                        },
                        {
                            id: "6",
                            value: "F"
                        },
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
        }
    ]
});

const userlistGrid = new dhx.Grid(null, {
    columns: [ // cols말고 columns 넘어야 한다.
        {
            id: "id",
            header: [{
                text: "ID"
            }]
        },
        {
            id: "name",
            header: [{
                text: "이름"
            }]
        },
        {
            id: "level",
            header: [{
                text: "레벨"
            }]
        },
        {
            id: "desc",
            header: [{
                text: "설명"
            }]
        },
        {
            id: "regDate",
            header: [{
                text: "가입일"
            }]
        }
    ],
    autoWidth: true,
    resizable: true,
    height: 442,
    data: []
});

layout.getCell("menu").attach(menu);
layout.getCell("filtercontent").attach(filterForm);
layout.getCell("userlistcontent").attach(userlistGrid);

filterForm.getItem("search").events.on("click", () => {
    const listValue = filterForm.getValue();
    console.log("listValue", listValue)
    const params = new URLSearchParams(listValue).toString();
    console.log("params", params)
    loadUserList(0, params);
})

function loadUserList(page = 0, params = null) {
    let url = `/user/list/${page}`;
    if (params && params.toString()) {
        url += "?" + params;
    }
    fetch(url)
        .then(res => {
            if (!res.ok) {
                throw res;
            }
            return res.json();
        })
        .then(data => {
            // 1) 먼저 데이터와 totalCount를 grid에 세팅
            userlistGrid.data.parse(data.content);
            userlistGrid.data.totalCount = data.totalElements;

            // 2) 기존 pagination 있으면 삭제
            if (window.pagination) {
                window.pagination.destructor();
            }

            // 3) pagination 객체 생성 (데이터/페이지크기 기반)
            window.pagination = new dhx.Pagination(null, {
                css: "dhx_widget--bordered dhx_widget--no-border_top",
                data: userlistGrid.data,
                pageSize: 10
            });

            // 4) attach
            layout.getCell("paginationcontent").attach(window.pagination);

            // 5) 페이지 수 자동 계산됨. UI의 현재 페이지 세팅 (0-based 주의)
            window.pagination.setPage(data.number);

            // 6) 페이지 이동 이벤트
            window.pagination.events.on("change", (newPageIdx) => {
                loadUserList(newPageIdx, params);
            });
        })
        .catch(e => {
            // 에러 처리
            console.error("조회 에러", e);
        });
}

