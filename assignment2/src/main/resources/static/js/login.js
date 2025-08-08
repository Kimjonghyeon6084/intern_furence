const root = document.getElementById("loginform");

const layout = new dhx.Layout(root, {
    rows: [
        {id: "loginlayout", height: "content"}
    ]
});

const form = new dhx.Form(null, {
    css: "dhx_widget--bordered",
    rows: [
        {
            type: "input",
            name: "id",
            label: "아이디",
        },
        {
            type: "text",
            name: "idError",
            html: "",
            css: "formerrormessage",
            height: "content",
            hidden: true
        },
        {
            type: "input",
            name: "pwd",
            label: "비밀번호",
            inputType: "password",
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
            type: "button",
            text: "로그인",
            name: "loginbutton",
        },
        {
            type: "text",
            name: "loginError",
            html: "",
            css: "loginerrormessage",
            height: "content",
            hidden: true,
        },
    ]
});

layout.getCell("loginlayout").attach(form);

form.getItem("loginbutton").events.on("click", function() {
    form.getItem("idError").hide();
    form.getItem("pwdError").hide();
    form.getItem("loginError").hide();

    const value = form.getValue();
    console.log("value:", value);
    fetch("/login", {
        method: "POST",
        headers: {"Content-Type" : "application/json"},
        body: JSON.stringify({id: value.id, pwd: value.pwd})
    })
    .then(async response => {
        if (!response.ok) {
            error = await response.json();
            console.log("error : ",error);

            if (error.success === false) {
                switch (error.error) {
                    case "id":
                        form.getItem("idError").setValue(error.message);
                        form.getItem("idError").show();
                        break;
                    case "pwd":
                        form.getItem("pwdError").setValue(error.message);
                        form.getItem("pwdError").show();
                        break;
                    default:
                        form.getItem("idError").hide();
                        form.getItem("pwdError").hide();
                }
                form.getItem("loginError").setValue("");
            }
            if (error.loginStatus === "FAILURE") {
                switch (error.loginValidField) {
                    case "id":
                        form.getItem("loginError").setValue(error.message || "존재하지 않는 아이디입니다.");
                        form.getItem("loginError").show();
                        break;
                    case "pwd":
                        form.getItem("loginError").setValue(error.message || "비밀번호가 틀렸습니다.");
                        form.getItem("loginError").show();
                        break;
                    default:
                        form.getItem("loginError").setValue(error.message || "로그인에 실패했습니다.");
                        form.getItem("loginError").show();
                }
                form.getItem("idError").setValue("");
                form.getItem("pwdError").setValue("");
            }
            return null;
        }
        window.location.href = "/api/userlist";
    })
    .catch((e) => {
        console.error("catch 오류 : ", e);
        alert("서버 오류 및 네트워크 오류");
    })
})