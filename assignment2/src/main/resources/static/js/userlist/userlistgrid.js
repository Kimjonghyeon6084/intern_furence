export function createUserListGrid() {
    const gridDiv = document.createElement("div");
    gridDiv.style.height = "340px";
    const grid = new dhx.Grid(gridDiv, {
        columns: [
            { id: "id", header: [{ text: "ID" }] },
            { id: "name", header: [{ text: "이름" }] },
            { id: "level", header: [{ text: "레벨" }] },
            { id: "desc", header: [{ text: "설명" }] },
            { id: "regDate", header: [{ text: "가입일" }] }
        ],
        data: [],
        autoWidth: true,
        resizable: true,
        height: 340
    });

    return {
        gridDiv,
        setData: (data) => grid.data.parse(data)
    };
}
