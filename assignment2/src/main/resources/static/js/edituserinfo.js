let layout;
let editForm;

function createLayout(rootId = "edituserinfo") {
    layout = new dhx.Layout(document.getElementById(rootId), {
        rows: [
            {
                id: "editcontent",
                height: "content",
            }
        ]
    })
}

function createEditForm() {
    editForm = new dhx.Form(null, {
        rows: [
            {
                type: "input",
                label: "아이디",
                name: "id",
                width: 220,
                readOnly: true,
            },
            {
                type: "text",
                name: "idError",
                html: "",
                css: "formerrormessage",
                height: "content",
                hidden: true,
            },
            {
                type: "input",
                label: "비밀번호",
                name: "pwd",
                width: 220,
            },
            {
                type: "text",
                name: "pwdError",
                html: "",
                css: "formerrormessage",
                height: "content",
                hidden: true,
            },
            {
                type: "input",
                label: "이름",
                name: "name",
                width: 220,
            },
            {
                type: "text",
                name: "nameError",
                html: "",
                css: "formerrormessage",
                height: "content",
                hidden: true,
            },
            {
                type: "combo",
                label: "level",
                name: "level",
//                width: 220,
                data: ["A","B","C","D","E","F"].map(v => ({
                                                        id: v,
                                                        value: v
                                                }))
            },
            {
                type: "text",
                name: "levelError",
                html: "",
                css: "formerrormessage",
                height: "content",
                hidden: true,
            },
            {
                type: "input",
                label: "설명",
                name: "desc",
                width: 250,
            },
            {
                type: "button",
                name: "edit",
                id: "edit",
                text:"수정",
                color:"primary",
                size:"medium"
            },
        ]
    })
    layout.getCell("editcontent").attach(editForm);

    editForm.getItem("edit").events.on("click", () => {

        signupForm.getItem("idError").hide();
        signupForm.getItem("pwdError").hide();
        signupForm.getItem("nameError").hide();
        signupForm.getItem("levelError").hide();

        const formValue = editForm.getValue();

        // 비밀번호가 빈칸이면 null로 바꿔서 전송 (수정 안 할 때)
        if (!formValue.pwd) formValue.pwd = null;

        // id는 URL에서 따로 빼서 사용
        const userId = formValue.id;

        fetch(`/user/${userId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                pwd: formValue.pwd,
                name: formValue.name,
                level: formValue.level,
                desc: formValue.desc,
                // 필요시 추가 필드
            })
        })
        .then(async res => {
            if (!res.ok) {
                const err = await res.json();
                console.log("err", err)
                console.log("수정 실패 : " + (err.message || "알 수 없는 오류"))
                return;
            }
            const result = await res.json();
            console.log("result", result)
            console.log("수정 완료")
            window.location.href = "/api/userlist"
        })
        .catch(() => dhx.alert("서버 오류 및 네트워크 오류"));
    });
}


function init() {
    createLayout();
    createEditForm();

    // URL에서 id 추출
        const params = new URLSearchParams(location.search);
        const userId = params.get("id");

        // 1. id로 기존 정보 조회 (비밀번호 제외)
        fetch(`/user/${userId}`)
            .then(res => res.json())
            .then(user => {
                // 2. input에 값 넣기 (setValue로 한 번에)
                editForm.setValue({
                    id: user.id,
                    pwd: "", // 비밀번호는 항상 빈칸
                    name: user.name,
                    level: user.level,
                    desc: user.desc
                });
            });
}

document.addEventListener("DOMContentLoaded", init);