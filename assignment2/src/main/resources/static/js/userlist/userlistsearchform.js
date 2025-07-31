// userlistSearchForm.js
export function createUserListSearchForm(onSearch, onReset) {
    const formDiv = document.createElement("div");
    const form = new dhx.Form(formDiv, {
        rows: [
            {
                cols: [
                    { type: "input",  name: "id",      placeholder: "ID",    width: 80 },
                    { type: "input",  name: "name",    placeholder: "이름",  width: 80 },
                    { type: "input",  name: "level",   placeholder: "레벨",  width: 60 },
                    { type: "input",  name: "desc",    placeholder: "설명",  width: 100 },
                    { type: "datepicker", name: "regDate", placeholder: "가입일", dateFormat: "%Y-%m-%d", width: 120 },
                    { type: "button", name: "search", text: "조회", color: "primary", size: "medium" },
                    { type: "button", name: "reset", text: "초기화", color: "secondary", size: "medium" }
                ]
            }
        ]
    });

    form.events.on("click", (e) => {
        if (e === "search") {
            const values = form.getValue();
            if (typeof onSearch === "function") onSearch(values);
        }
        if (e === "reset") {
            form.clear();
            if (typeof onReset === "function") onReset();
        }
    });

    return formDiv;
}
