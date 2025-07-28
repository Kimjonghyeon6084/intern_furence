import { renderLoginPage } from "/js/login.js";
import { renderUserListPage } from "/js/userlist.js";
import { renderFileUploadPage } from "/js/fileupload.js";

function renderPage() {
    const hash = location.hash || "#login"; // 기본은 로그인 페이지로

    const app = document.getElementById("app");
    app.innerHTML = "";

    switch (hash) {
        case "#login":
            renderLoginPage();
            break;

        case "#userlist":
            renderUserListPage();
            break;

        case "#fileupload":
            renderFileUploadPage();
            break;

        default:
            renderLoginPage();
    }
}

// hash 변경될 때마다 renderPage 호출
window.addEventListener("hashchange", renderPage);

renderPage();