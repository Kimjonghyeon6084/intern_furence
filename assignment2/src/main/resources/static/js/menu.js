export function createMenu(onSelect) {

    const menuDiv = document.createElement("div");
    const menuForm = new dhx.Form(menuDiv, {
        rows: [
            {
                type: "button",
                id:"userlist",
                text:"유저리스트",
                color:"primary",
                size: "medium"
            },
            {
                type: "button",
                id: "fileupload",
                text: "파일업로드",
                color: "secondary",
                size: "medium"
            }
        ]
    });

    menuForm.events.on("click", id => {
        if (typeof onSelect === "function") {
            onSelect(id);
        }
    })
    return menuDiv;
}