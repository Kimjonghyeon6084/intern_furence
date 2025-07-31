function createMenu(onSelect, targetCell) {
    const menuForm = new dhx.Form(targetCell, {
        rows: [
            { type: "button", id: "userlist", text: "유저리스트", color: "primary" },
            { type: "button", id: "fileupload", text: "파일업로드", color: "secondary" }
        ]
    });
    menuForm.events.on("click", id => {
        if (typeof onSelect === "function") onSelect(id);
    });
}