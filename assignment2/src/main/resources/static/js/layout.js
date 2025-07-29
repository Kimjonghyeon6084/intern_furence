//export function createMainLayout(targetId = "app") {
//
//    const app = document.getElementById(targetId);
//        while (app.firstChild) {
//                app.removeChild(app.firstChild);
//    }
//
//    const layout = new dhx.Layout(targetId, {
//        rows: [
//            { id: "header", height: 60 },
//            {
//                id: "body",
//                cols: [
//                    { id: "menu", width: 180 },
//                    { id: "content" }
//                ]
//            },
//            { id: "footer", height: 40 }
//        ]
//    });
//    return layout;
//}
export function createMainLayout(targetId = "app") {
    // 1. app div 내부 비우기 (초기화)
    const app = document.getElementById(targetId);
    while (app.firstChild) {
        app.removeChild(app.firstChild);
    }

    // 2. dhtmlx Layout 생성
    const layout = new dhx.Layout(targetId, {
        rows: [
            { id: "header", height: 60 },
            {
                id: "body",
                cols: [
                    { id: "menu", width: 180 },
                    { id: "content" }
                ]
            },
            { id: "footer", height: 40 }
        ]
    });

    // 3. 각 cell에 div(Node) attach (추후 Form/Grid 붙일 div)
    //    - header/footer/menu/content 등 필요한 만큼!
    //    - 반드시 div만 attach!

    const headerDiv = document.createElement("div");
    layout.getCell("header").attach(headerDiv);
    console.log("headerDiv attach 확인",headerDiv)


    const menuDiv = document.createElement("div");
    layout.getCell("menu").attach(menuDiv);
    console.log("menuDiv attach 확인",menuDiv)

    const contentDiv = document.createElement("div");
    layout.getCell("content").attach(contentDiv);
    console.log("contentDiv attach 확인",contentDiv)

    const footerDiv = document.createElement("div");
    layout.getCell("footer").attach(footerDiv);
    console.log("footerDiv attach 확인",footerDiv)

    // 필요하다면 div들도 반환하거나 layout과 함께 전달 가능
    // 예시: return { layout, headerDiv, menuDiv, contentDiv, footerDiv };

    console.log('menuDiv:', menuDiv, menuDiv instanceof HTMLElement);
    console.log('headerDiv:', headerDiv, headerDiv instanceof HTMLElement);
    console.log('footerDiv:', footerDiv, footerDiv instanceof HTMLElement);

    return  { layout, headerDiv, menuDiv, contentDiv, footerDiv };
}
