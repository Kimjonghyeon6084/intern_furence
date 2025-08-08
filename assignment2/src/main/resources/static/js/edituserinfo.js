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
    css: "dhx_widget--bordered",
        rows: [
            {
                type: "input",
                label: "아이디",
                name: "id",
                width: 300,
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
                inputType: "password",
                width: 300,
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
                width: 300,
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
                css: "levelcontent",
                width: 230,
                data: [
                    {
                        id: "A",
                        value: "A",
                    },
                    {
                        id: "B",
                        value: "B",
                    },
                    {
                        id: "C",
                        value: "C",
                    },
                    {
                        id: "D",
                        value: "D",
                    },
                    {
                        id: "E",
                        value: "E",
                    },
                    {
                        id: "F",
                        value: "F",
                    },
                ],
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
                width: 300,
            },
            {
                type: "button",
                name: "edit",
                id: "edit",
                text:"수정",
                color:"primary",
                size:"medium",
                css: "editmutton"
            },
        ]
    })
    layout.getCell("editcontent").attach(editForm);

    editForm.getItem("edit").events.on("click", () => {

        editForm.getItem("idError").hide();
        editForm.getItem("pwdError").hide();
        editForm.getItem("nameError").hide();
        editForm.getItem("levelError").hide();

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
            })
        })
        .then(async res => {
            if (!res.ok) {
                const err = await res.json();
                console.log("err", err)
                console.log(err.message)
                err.error.forEach(errorList => {
                    switch (errorList.field) {
                        case "id":
                            editForm.getItem("idError").setValue(errorList.message);
                            editForm.getItem("idError").show();
                            break;
                        case "pwd":
                            editForm.getItem("pwdError").setValue(errorList.message);
                            editForm.getItem("pwdError").show();
                            break;
                        case "level":
                            editForm.getItem("levelError").setValue(errorList.message);
                            editForm.getItem("levelError").show();
                            break;
                        case "name":
                            editForm.getItem("nameError").setValue(errorList.message);
                            editForm.getItem("nameError").show();
                            break;
                        default:
                            break;
                    }
                });
                return null;
            }
            return res.json();
        })
        .then(data => {
            if (!data) return;
            console.log("data", data)
            console.log("수정 완료")
            window.location.href = "/api/userlist"
        })
        .catch((e) => {
            console.log("e", e);
            alert("서버 오류 및 네트워크 오류")
        });
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