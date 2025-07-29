export function createUserListSearchForm(onSearch, onReset) {
    // 1. dhtmlx Form 구성
    const formDiv = document.createElement("div");
    const form = new dhx.Form(formDiv, {
        css: "furence-custom-form",
        padding: 0,
        rows: [
            {
                cols: [
                    { type: "input",  name: "id",      placeholder: "ID",    width: 80 },
                    { type: "input",  name: "name",    placeholder: "이름",  width: 80 },
                    { type: "input",  name: "level",   placeholder: "레벨",  width: 60 },
                    { type: "input",  name: "desc",    placeholder: "설명",  width: 100 },
                    { type: "datepicker", name: "regDate", placeholder: "가입일", dateFormat: "%Y-%m-%d", width: 120 },
                    { type: "button", name: "search", text: "조회", color: "primary", size: "medium" },
                    { type: "button", name: "reset", text: "초기화", color: "secondary", size: "medium", css: "marginLeft10" }
                ]
            }
        ]
    });

    // 2. 이벤트 연결
    form.events.on("click", (name) => {
        if (name === "search") {
            const values = form.getValue();
            if (typeof onSearch === "function") onSearch(values);
        }
        if (name === "reset") {
            form.clear();
            if (typeof onReset === "function") onReset();
        }
    });

    return formDiv;
}
