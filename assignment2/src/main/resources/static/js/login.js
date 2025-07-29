// 로그인 dhtmlx 폼 구성
// 아이디, 패스워드, 로그인 버튼만 있으므로 레이아웃으로 구성하지 말고 폼으로 만들어보자.

export function renderLoginPage() {

    // 기존 #app 내부를 로그인폼으로 교체
    const app = document.getElementById("app");

    // 기존에 attach가 되어 있을 수도 있는 요소들을 비워주기.
    while (app.firstChild) {
        app.removeChild(app.firstChild);
    }

    const formDiv = document.createElement("div");
    formDiv.id = "loginform";
    app.appendChild(formDiv);

    // dhtmlx로 로그인 폼 만들기
    const loginform = new dhx.Form("loginform" , {
        rows : [
            {
                type : "input",
                name: "id",
                label: "아이디"
             },
            {
                type: "input",
                name: "pwd",
                label: "비밀번호",
                inputType: "password"
            },
            {
                type: "button",
                text: "로그인",
                size: "medium",
                color: "primary",
                id: "loginbutton"
            }
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
        .then(async res => {
            const data = await res.json();
            if(!res.ok || (data.loginResultResponse && data.loginResultResponse.status == "FAILURE")) {
                const message =
                    (data.loginResultResponse && data.loginResultResponse.message)
                        ? data.loginResultResponse.message
                        : "아이디 혹은 비밀번호를 확인해주세요.";
                        alert(message);
                        return;
            }
            window.location.hash = "#main";
        })
        .catch(e => {
            alert("서버 오류 또는 네트워크 장애가 발생했습니다.");
        })
    })
}