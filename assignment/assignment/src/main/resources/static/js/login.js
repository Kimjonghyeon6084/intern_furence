document.getElementById("loginForm").addEventListener("submit", function (e) {

    e.preventDefault();

    const id = document.getElementById("id").value;
    const pwd = document.getElementById("pwd").value;

    fetch("/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({id, pwd})
    })
    .then(async res => {
        if (res.ok) {
            window.location.href = "/userlist";
        } else {
            let errorMessage = "알 수 없는 오류가 발생했습니다.";
            try {
                const err = await res.json();
                errorMessage = err.message || errorMessage;
            } catch (e) {
                alert("JSON 파싱 실패", e);
            }

            // 필드별로 나눠 보여주는 로직
            const idError = document.getElementById("idError");
            const pwdError = document.getElementById("pwdError");
            const loginFail = document.getElementById("loginFail");
            idError.textContent = "";
            pwdError.textContent = "";
            loginFail.textContent = "";

            errorMessage.split("\n").forEach(msg => {

            //console.log(msg.replace("알 수 없는 오류: ", ""))
            const formattingMsg = msg.replace("알 수 없는 오류: ", ""); // 미리 보기 좋기 수정

                if (formattingMsg.includes("아이디") && !formattingMsg.includes("비밀번호")) {
                    idError.textContent += formattingMsg + "\n";
                } else if (formattingMsg.includes("비밀번호")) {
                    pwdError.textContent += formattingMsg + "\n";
                } else if(formattingMsg.includes("모두")) {
                    console.log("지나가냐?11111");
                    loginFail.textContent += formattingMsg;
                    console.log("지나가냐?222222");
                } else {
                    alert(formattingMsg); // 기타 메시지는 알림창
                }
            });

            console.log("login 실패");
        }
    })
    .catch(err => {
        alert("서버 오류");
    });
});

