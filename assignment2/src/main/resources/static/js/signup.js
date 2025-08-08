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
        css: "dhx_widget--bordered",
        rows: [
            {
                cols: [
                    {
                        type: "input",
                        label: "아이디",
                        name: "id",
                        height: "content",
                        width: 300,
                        css: "idinputcss"
                    },
                    {
                        type: "button",
                        name: "idduplicationcheckbutton",
                        height: "content",
                        text: "중복체크",
                        width: "content",
                        css: "idduplicationcheckbuttoncss"
                    },
                ],
                width: "content",
                css: "idcheckcontent"
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
                inputType: "password",
                label: "비밀번호",
                height: "content",
                name: "pwd",
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
                height: "content",
                width: 300,
                css: "levelcombo",
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
                width: 300,
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
                height: "content",
                css: "levelcontent",
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
                height: "content",
                css: "descdiv",
            },
            {
                type: "button",
                name: "register",
                id:"register",
                text:"등록",
                color:"primary",
                size:"medium",
                css: "signupbutndiv",
            },
        ],
        height: "content",
    })
    layout.getCell("usersignupcontent").attach(signupForm);
    signupForm.getItem("idduplicationcheckbutton").events.on("click", () => {

        const idValue = signupForm.getItem("id").getValue();
        console.log("idValue",idValue);

        signupForm.getItem("idError").hide();

        const params = new URLSearchParams({
            id : idValue
        })

        fetch(`/user/checkId?id=${params}`)
            .then(async res => {
                if(!res.ok) {
                    const error = await res.json();
                    console.log("error", error);
                    alert(error);
                    return;
                }
                return res.json();
            })
            .then(data => {
                console.log("data", data);
                if (data.duplicate) {
                    signupForm.getItem("idError").setValue("아이디가 중복됩니다.");
                    signupForm.getItem("idError").show();
                } else {
                    signupForm.getItem("idError").setValue("사용가능한 아이디입니다..");
                    signupForm.getItem("idError").show();
                }
            })
            .catch(error => {
                alert(err || "중복 체크 중 오류가 발생하였습니다.")
            })
    })

    signupForm.getItem("register").events.on("click", () => {

        signupForm.getItem("idError").hide();
        signupForm.getItem("pwdError").hide();
        signupForm.getItem("nameError").hide();
        signupForm.getItem("levelError").hide();

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
                err.error.forEach(errorList => {
                    // 각 에러의 field에 해당하는 에러 영역이 있으면 메시지 세팅 및 표시
                    switch (errorList.field) {
                        case "id":
                            signupForm.getItem("idError").setValue(errorList.message);
                            signupForm.getItem("idError").show();
                            break;
                        case "pwd":
                            signupForm.getItem("pwdError").setValue(errorList.message);
                            signupForm.getItem("pwdError").show();
                            break;
                        case "level":
                            signupForm.getItem("levelError").setValue(errorList.message);
                            signupForm.getItem("levelError").show();
                            break;
                        case "name":
                            signupForm.getItem("nameError").setValue(errorList.message);
                            signupForm.getItem("nameError").show();
                            break;
                        default:
                            break;
                    }
                });
            } else {
                window.location.href = "/api/login";
            }
        })
        .catch((e) => {
            console.log("e", e);
            alert("서버 오류 및 네트워크 오류")
        });
    })
}


function init() {
    createLayout();
    createSignupForm();
}

document.addEventListener("DOMContentLoaded", init);