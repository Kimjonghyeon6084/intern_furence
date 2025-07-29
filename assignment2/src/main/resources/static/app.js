//import { renderLoginPage } from "/js/login.js";
//import { renderUserListPage } from "/js/userlist.js";
//import { renderFileUploadPage } from "/js/fileupload.js";
//import { renderMainPage} from "/js/main.js"
//
//function renderPage() {
//    const hash = location.hash || "login"; // 기본은 로그인 페이지로
//
//    const app = document.getElementById("app");
//    app.innerHTML = "";
//
//    switch (hash) {
//        case "login":
//            renderLoginPage();
//            break;
//
//        case "userlist":
//            renderUserListPage();
//            break;
//
//        case "fileupload":
//            renderFileUploadPage();
//            break;
//
//        case "main":
//            renderMainPage();
//            break;
//
//        default:
//            renderLoginPage();
//    }
//}
//
//// hash 변경될 때마다 renderPage 호출
//window.addEventListener("hashchange", renderPage);
//
//renderPage();

import { createMainLayout } from "/js/layout.js";
import { createMenu } from "/js/menu.js";
import { createUserListView } from "/js/userlist/userlist.js";
import { createFileUploadForm } from "/js/fileupload.js";
import { renderLoginPage } from "/js/login.js";

// SPA hash 기반 라우팅
function renderPage() {

    if (!window.location.hash) {
        window.location.hash = "#login";
        return;
    }

    const hash = window.location.hash || "#login";
        if (hash === "#main") {
            renderMainPage();
        } else {
            renderLoginPage();
        }
    }

window.addEventListener("DOMContentLoaded", renderPage);
window.addEventListener("hashchange", renderPage);

function renderMainPage() {

    const  { layout, headerDiv, menuDiv, contentDiv, footerDiv } = createMainLayout("app");
    console.log("layout : ",layout)

//    const menuDiv = createMenu(onMenuSelect);
//    layout.getCell("menu").attach(menuDiv);
//    console.log("menuDiv attach 확인",menuDiv)
//
//
//    // ⭐️ header/footer에 dhtmlx Form을 attach 하고 싶으면:
//    // 1. div 만들고
//    // 2. div attach
//    // 3. 그 div에 Form 생성
//    const headerDiv = document.createElement("div");
//    layout.getCell("header").attach(headerDiv);
//    console.log("headerDiv attach 확인",headerDiv)
//
//    const headerForm = new dhx.Form(headerDiv, {
//        rows: [
//            { type: "text", label: "header test", value: "", readonly: true }
//        ]
//    });
//
//    const footerDiv = document.createElement("div");
//    layout.getCell("footer").attach(footerDiv);
//
//    const footerForm = new dhx.Form(footerDiv, {
//        rows: [
//            { type: "text",
//              label: "Copyright 2025",
//              value: "",
//              readonly: true }
//        ]
//    });
//    console.log("footerDiv attach 확인",footerDiv)
    createMenu(onMenuSelect, menuDiv);

    // 헤더/푸터도 마찬가지
    new dhx.Form(headerDiv, {
        rows: [
            { type: "text", label: "header test", value: "", readonly: true }
        ]
    });

    new dhx.Form(footerDiv, {
        rows: [
            { type: "text",
              label: "Copyright 2025",
              value: "",
              readonly: true }
        ]
    });

    showContent("userlist");

    function onMenuSelect(page) {
            showContent(page);
        }

    function showContent(page) {
//        const contentCell = layout.getCell("content");
//        contentCell.detach(); // 셀 내용 비우기
        contentDiv.innerHTML = ""; // 내용 비우기

        let comp = null;

        switch (page) {
//             case "userlist":
//                comp = createUserListGrid();
//                break;

             case "fileupload":
                comp = createFileUploadForm();
                break;

             case "userlist":
                comp = createUserListView();
                break;

            default:
                comp = null;
        }
        console.log("showContent - page:", page, "comp:", comp, "isDiv:", comp instanceof HTMLElement);

        if (comp) {
          // ⭐️ comp는 반드시 div(Node)만 반환해야 함!
//            contentCell.attach(comp);
        contentDiv.appendChild(comp);
        }
    }
}