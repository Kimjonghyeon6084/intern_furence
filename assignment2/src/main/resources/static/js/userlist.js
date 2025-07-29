let lastSearchParams = null;
let currentPage = 0;
let size = 10;



// 여긴 레이아웃으로 만들어서 파일업로드 화면과 유저리스트 화면을 레이아웃안에 넣어보자.

export function createUserListGrid() {

    // 기존 #app 내부를 userlist 화면으로 교체
    const app = document.getElementById("app");
//    while (app.firstChild) {
//        app.removeChild(app.firstChild);
//    }

     // 레이아웃 생성
        const grid = new dhx.Grid(null, {
            columns: [
                { id: "id", header: [{ text: "ID" }] },
                { id: "name", header: [{ text: "이름" }] },
                { id: "level", header: [{ text: "레벨" }] },
                { id: "desc", header: [{ text: "설명" }] },
                { id: "regDate", header: [{ text: "regDate" }]}
            ],
            data: []
        });


//  각 요소 attach
//    layout.getCell("header").attachHTML(`<div>헤더</div>`);
//    layout.getCell("body").attach(
//        new dhx.Grid
//    );
//    layout.getCell("footer").attachHTML(`<div>푸터</div>`);


// 유저리스트 보여주는 grid 생성 + 페이징버튼
//    layout.getCell("list").attachHTML(
//        `<div id="userlistgrid" style="height:350px;"></div>
//         <div id="pagination" style="margin-top:20px;text-align:center;"></div>`
//    )
//
//    layout.getCell("menu").attachHTML(
//        `<div id="menu"></div>`
//    )

    function fetchUserList(page = 0, params = null) {
        let url = `/user/list/${page}`;
        if (params && params.toString()) {
            url += "?" + params.toString();
        }
        fetch(url)
            .then(res => {
                if (!res.ok) {
                    throw res;
                }
                return res.json();
            })
            .then(data => {
            }
        )
    }
}

