var loginForm;
var hash;

function renderPage() {

    hash = window.location.hash || "#login";
    if (hash === "#main" || hash === "#userlist" || hash === "#fileupload") {
        renderMainPage();
    } else {
        renderLoginPage();
    }
}
window.addEventListener("DOMContentLoaded", renderPage);
window.addEventListener("hashchange", renderPage);

// --- 로그인 화면 ---
function renderLoginPage() {
    const app = document.getElementById("app");
    app.innerHTML = "";

    loginForm = new dhx.Form(app, {
        rows: [
            { type: "input", name: "id", label: "아이디" },
            { type: "input", name: "pwd", label: "비밀번호", inputType: "password" },
            { type: "button", text: "로그인", id: "loginBtn", color: "primary" }
        ]
    });

    // loginBtn이 실제로 존재하는지 꼭 확인(디버깅!)
        console.log("loginForm.getItem(loginBtn):", loginForm.getItem("loginBtn"));

    // 버튼이 있을 때만 이벤트 연결
    if (loginForm.getItem("loginBtn")) {
        loginForm.getItem("loginBtn").events.on("click", function() {
            const values = loginForm.getValue();
            console.log("로그인 폼 값:", values);
            fetch("/login", {
                        method: "POST",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify({ id: values.id, pwd: values.pwd })
                    })
                    .then(async res => {
                        const data = await res.json();
                        if (!res.ok || (data.loginResultResponse && data.loginResultResponse.status == "FAILURE")) {
                            alert((data.loginResultResponse && data.loginResultResponse.message) || "아이디/비번 오류!");
                            return;
                        }
                        window.location.hash = "#main";
                    })
                    .catch(() => alert("서버 오류 또는 네트워크 장애!"));
        });
    }
}

// --- 메인+유저리스트 화면 ---
function renderMainPage() {
    const app = document.getElementById("app");
    app.innerHTML = "";

    // dhtmlx Layout (셀 id 기반!)
    var layout = new dhx.Layout(app, {
        rows: [
            { id: "header", height: 60 },
            {
                id: "body",
                cols: [
                    { id: "menu", width: 160 },
                    { id: "content" }
                ]
            },
            { id: "footer", height: 40 }
        ]
    });

    // Header/Footer mount
    new dhx.Form(layout.getCell("header"), {
        rows: [
            { type: "text", label: "관리자 SPA 예제", value: "", readonly: true }
        ]
    });
    new dhx.Form(layout.getCell("footer"), {
        rows: [
            { type: "text", label: "Copyright 2025", value: "", readonly: true }
        ]
    });

    // 메뉴 mount
//    createMenu(function(page) {
//        layout.getCell("content").detach();
//        if (page === "userlist") {
//            createUserListView(layout.getCell("content"));
//        } else if (page === "fileupload") {
//            layout.getCell("content").attachHTML("<div style='padding:40px'>파일 업로드 구현 예정</div>");
//        }
//    }, layout.getCell("menu"));
    createMenu(function(page) {
        layout.getCell("content").detach();
        window.location.hash = "#" + page;
        if (page === "userlist") {
            createUserListView(layout.getCell("content"));
        } else if (page === "fileupload") {
            layout.getCell("content").attachHTML("<div style='padding:40px'>파일 업로드 구현 예정</div>");
        }
    }, layout.getCell("menu"));

//    // 초기 content 셀은 아무것도 출력하지 않거나 안내 메시지만 출력
//    layout.getCell("content").attachHTML(
//        "<div style='padding:50px; text-align:center; color:#999'>좌측 메뉴에서 기능을 선택하세요.</div>"
//    );
    if (hash === "#userlist") {
        createUserListView(layout.getCell("content"));
    } else if (hash === "#fileupload") {
        layout.getCell("content").attachHTML("<div style='padding:40px'>파일 업로드 구현 예정</div>");
    } else {
        layout.getCell("content").attachHTML(
            "<div style='padding:50px; text-align:center; color:#999'>좌측 메뉴에서 기능을 선택하세요.</div>"
        );
    }
}
