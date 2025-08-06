let layout;
let signupForm;

function createLayout(rootId = "usersignupform") {
    layout = new dhx.Layout(document.getElementById(rootId), {
        rows: [
            {
                id: "usersignupcontent",
                height: "content",
            },
        ]
    })
}


function createSignupForm() {
    signupForm = new dhx.Form(null, {
        rows: [
            {
                type: "input",
                label: "아이디",
                name: "id",
                width: 220,
            },
            {
                type: "password",
                label: "비밀번호",
                name: "pwd",
                width: 220,
            },
            {
                type: "input",
                label: "이름",
                name: "name",
                width: 220,
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
                type: "input",
                label: "설명",
                name: "desc",
                width: 250,
            },
            {
                type: "button",
                name: "register",
                id:"register",
                text:"등록",
                color:"primary",
                size:"medium"
            },
            {
                type: "text",
                name: "login-error",
                id: "login-error",
                label: "",
                value: "",
                hidden: true,
                css: "dhx_form-global-error"
            }
        ]

    })
    layout.getCell("usersignupcontent").attach(signupForm);
    signupForm.getItem("register").events.on("click", () => {
        const formValue = signupForm.getValue();
        console.log("formValue", formValue);
        fetch("/user/signup", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(formValue)
        })
        .then(async res =>{
            if (!res.ok) {
                const err = await res.json();
                console.log("err", err);
                showLoginFailMessage(err);
            } else {
                window.location.href = "/api/login";
            }
        })
        .catch(() => dhx.alert("서버 오류 및 네트워크 오류"));
    })
}

function showLoginFailMessage(err) {
    console.log("err1" + err)
    const item = signupForm.getItem("login-error");

    item.config.value = "아이디 또는 비밀번호가 올바르지 않습니다.";
    item.config.hidden = false;
    signupForm.paint();
}
function hideLoginFailMessage() {
    const item = signupForm.getItem("login-error");
    if (item) {
        item.config.value = "";
        item.config.hidden = true;
        signupForm.paint();
    }
}


function init() {
    createLayout();
    createSignupForm();
}

document.addEventListener("DOMContentLoaded", init);