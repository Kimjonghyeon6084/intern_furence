let lastSearchParams = null;
let size = 10;
let grid = null;


// 여긴 레이아웃으로 만들어서 파일업로드 화면과 유저리스트 화면을 레이아웃안에 넣어보자.

export function renderUserListPage() {

    // 기존 #app 내부를 userlist 화면으로 교체
    const app = document.getElementById("app");
    app.innerHTML = '<div id = "userlistgrid"></div>';

    // 레이아웃 생성
    const layout = new dhx.Layout("userlistlayout", {
        rows: [
            {id: "header", height: 60},
            {
                cols: [
                    {id: "menu", width: 180},
                    {id: "body"}
                ],
                gravity : 2
            },
            {id: "footer", height: 40}
        ]
    })

//  각 요소 attach
    layout.getCell("header").attachHTML(`<div style="font-size:1.3rem;font-weight:bold;padding:18px;">유저 리스트 - 헤더</div>`);
    layout.getCell("footer").attachHTML(`<div style="text-align:center;padding:10px 0;color:#999;">Copyright ⓒ 2025</div>`);
    layout.getCell("menu").attachHTML(`
        <div style="padding:18px;">
            <button onclick="alert('메뉴 예시');">메뉴1</button>
            <br><button onclick="alert('메뉴2');">메뉴2</button>
        </div>
    `);
}

// 유저리스트 보여주는 grid 생성 + 페이징버튼
    layout.getcell("list").attachHTML(
        `<div id="userlist-grid" style="height:350px;"></div>
         <div id="pagination" style="margin-top:20px;text-align:center;"></div>`
    )

    layout.getcell("menu").attachHTML(
        `<div id="menu"></div>`
    )

    function fetchUserList(page = 0, params = null) {
        let url = `/user/list/${page}`;
        if (params && params.toString()) {
            url += "?" + params.toString();
        }
        fetch(url) {
            .then(res => {
                if (!res.ok) {
                    throw res;
                }
                return res.json();
            })
            .then(data => {

            })
        }
    }