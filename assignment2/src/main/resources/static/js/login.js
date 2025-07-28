// 로그인 dhtmlx 폼 구성
// 아이디, 패스워드, 로그인 버튼만 있으므로 레이아웃으로 구성하지 말고 폼으로 만들어보자.

export function renderLoginPage() {

    // 기존 #app 내부를 로그인폼으로 교체

    const app = document.getElementById("app");
    app.innerHTML = '<div id = "loginform"></div>';

    const loginform = new dhx.Form("loginform", {
        rows : [
            {type : "input", name: "id", label: "아이디"},
            {type: "input", name: "pwd", label: "비밀번호", inputType: "password"},
            {type: "button", text: "로그인", size: "medium", color: "primary", id: "loginbutton"}
        ]
        });

    // 로그인 버튼 클릭 이벤트

    loginform.getItem("loginbutton").events.on("click", () => {
        const values = loginform.getValue();

        fetch("/login", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                id: values.id,
                pwd: values.pwd
            })
        })
        .then(res => {
            if(!res.ok) throw new Error("로그인 실패");
            return res.json();
        })
        .then(data => {
            alert(data.message);
            window.location.hash = "#userlist";
        })
        .catch(e => {
            alert("아이디 혹은 비밀번호를 확인해주세요.")
        })
    })
}

