// main dhtmlx 폼 구성
// 여기서 왼쪽 메뉴에 버튼 클릭시 해당하는 화면 렌더링.

export function renderMainPage() {

    const app = document.getElementById("app");
    while (app.firstChild) {
            app.removeChild(app.firstChild);
    }


}

