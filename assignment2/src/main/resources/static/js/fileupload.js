// 전역 변수 선언
let layout;
let fileuploadForm;
let fileuploadResultForm;
let fileuploadListGrid;

const reloadFileuploadListGrid = () => {
    if (!fileuploadListGrid) {
        createFileuploadListGrid();
    }
    fetch("/api/userlist/data")
        .then(res => res.json())
        .then(list => {
            // 필요하다면 날짜 포맷 가공
            const converted = [];
            for (let i = 0; i < list.length; i++) {
                const row = list[i];
                converted.push({
                    ...row,
                    regDate: formatRegDate_DEBUGONLY(row.regDate)
                });
            }
            console.log("converted", converted)
            fileuploadListGrid.data.parse(converted);
        })
        .catch((e) => alert(e));
};

// 날짜 포맷 변환 함수
function formatRegDate_DEBUGONLY(dateStr) {
    if (!dateStr || typeof dateStr !== "string") return "";
    const date = new Date(dateStr.replace(" ", "T"));
    if (isNaN(date.getTime())) return "";
    return `${date.getFullYear()}년${date.getMonth() + 1}월${date.getDate()}일 `
         + `${date.getHours()}시${date.getMinutes().toString().padStart(2, '0')}분`;
}

// 초기화 함수
const init = () => {
    createLayout();
    createFileuploadForm();

    // 업로드 버튼 이벤트 바인딩 (폼 생성 후에 반드시 연결!)
        fileuploadForm.events.on("click", (id, e) => {
                const fileInput = document.getElementById("fileupload");
                const file = fileInput.files[0];
                if (!file) {
                    alert("파일을 선택하세요!");
                    return;
                }
                const formData = new FormData();
                formData.append("file", file);

                fetch("/upload", {
                    method: "POST",
                    body: formData
                })
                .then(async res => {
                    if (!res.ok) {
                        const error = await res.json();
                        alert(error.message);
                        return;
                    }
                    return res.json();
                    console.log("!1111111111111111")
                })
                .then(result => {
                    if (!result) return;
                    console.log("result", result)
                    // 업로드 결과 폼 그리기(기존 폼 있으면 지우고 새로)
                    if (layout.getCell("fileuploadResultContent").firstChild) {
                        layout.getCell("fileuploadResultContent").detach();
                    }
                    createFileuploadResultForm(result);
                })
                .catch(() => alert("업로드 중 오류 발생!"));
        });
};

// window.onload 또는 DOMContentLoaded에서 호출
document.addEventListener("DOMContentLoaded", init);
