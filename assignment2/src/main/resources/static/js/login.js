const root = document.getElementById("loginform");

const layout = new dhx.Layout(root, {
    rows: [
        {id: "loginlayout", height: "content"}
    ]
});

const form = new dhx.Form(null, {
    css: "dhx_widget--bordered", // 이건 무슨 css지?
    rows: [
        {type: "input", name: "id", label: "아이디", required: "true"},
        {type: "input", name: "pwd", label: "비밀번호", inputType: "password", required: "true"},
        {type: "button", text: "로그인",name: "로그인", name: "idbutton"}
    ]
});

layout.getCell("loginlayout").attach(form);

console.log("form.getItem(loginBtn):", form.getItem("idbutton"));


form.getItem("idbutton").events.on("click", function() {
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
            console.log("err : ",error);
            return null;
        }
        window.location.href = "/api/userlist";
    })
    .catch(() => alert("서버 오류 및 네트워크 오류"));
})