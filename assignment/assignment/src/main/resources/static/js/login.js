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
                console.log(errorMessage);
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

//            console.log(msg.replace("알 수 없는 오류: ", ""))
            console.log(msg);

                if (msg.includes("아이디") && !msg.includes("비밀번호")) {
                    idError.textContent += msg + "\n";
                } else if (!msg.includes("아이디") && msg.includes("비밀번호")) {
                    pwdError.textContent += msg + "\n";
                } else if(msg.includes("모두")) {
                    loginFail.textContent += msg;
                } else {
                    alert(msg); // 기타 메시지는 알림창
                }
            });

            console.log("login 실패");
        }
    })
    .catch(err => {
        alert("서버 오류");
    });
});

