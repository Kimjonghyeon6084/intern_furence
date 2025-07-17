let currentPage = 0;
const size = 10;

// 현재 주소의 page 받아오기
function getPageFromUrl() {
    const params = new URLSearchParams(window.location.search);
    return parseInt(params.get('page')) || 0;
}

// 페이지 이동(버튼 클릭 시)
function movePage(page) {
    currentPage = page;
    const url = new URL(window.location.origin + `/userlist/${page}`);
        window.history.pushState({}, '', url);
        fetchUserList(page);
}




// 데이터 테이블형태로 보이게 함
function renderTable(users) {
    const tbody = document.querySelector("#userTable tbody");
    tbody.innerHTML = "";
    if (!users || users.length === 0) {
        const row = `<tr><td colspan="5">데이터가 없습니다.</td></tr>`;
        tbody.insertAdjacentHTML('beforeend', row);
        return;
    }
    users.forEach(user => {
        const regDate = user.regDate
            ? new Date(user.regDate).toLocaleString('ko-KR', { hour12: false })
            : '';
        const row = `
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.level}</td>
                <td>${user.desc || ''}</td>
                <td>${regDate}</td>
            </tr>
        `;
        tbody.insertAdjacentHTML('beforeend', row);
    });
}

// 뒤로가기/앞으로가기
window.addEventListener('popstate', () => {
    const page = getPageFromUrl();
    currentPage = page;
    fetchUserList(page);
});

//
function renderPagination(current, totalPages, isLast) {
    const pageDiv = document.getElementById("pagination");
    pageDiv.innerHTML = "";

    const pageSize = 10;
    const currentGroup = Math.floor(current / pageSize);
    const startPage = currentGroup * pageSize;
    let endPage = startPage + pageSize - 1;
    if (endPage >= totalPages) {
        endPage = totalPages - 1;
    }

    if (startPage > 0) {
        pageDiv.innerHTML += `<button onclick="movePage(${startPage - 1})">이전</button>`;
    }

    for (let i = startPage; i <= endPage; i++) {
        pageDiv.innerHTML += `
            <button onclick="movePage(${i})" ${i === current
                                                    ? 'style="background:#777c7b;"'
                                                    : ''}>
                ${i + 1}
            </button>
        `;
    }

    if (!isLast && endPage < totalPages - 1) {
        pageDiv.innerHTML += `<button onclick="movePage(${endPage + 1})">다음</button>`;
    }
}

// 페이지 로딩될 때 /user/list/{page} 로 get요청
window.addEventListener('DOMContentLoaded', () => {
    const pathParts = window.location.pathname.split('/');
    let page = 0;
    if (pathParts.length >= 3 && pathParts[1] === 'userlist') {
        page = parseInt(pathParts[2]) || 0;
    }
    currentPage = page;
    fetchUserList(currentPage);
});

// 유저 리스트 불러오기
function fetchUserList(page = 0) {
    fetch(`/user/list/${page}`)
        .then(async res => {
            if (!res.ok) {
                let msg;
                try {
                    const data = await res.json();
                    msg = data.message;
                } catch {
                    msg = await res.text();
                }
                alert(msg);
                if (res.status === 401) {
                    window.location.href = "/login";
                }
                throw new Error(msg);
            }
            return res.json();
        })
        .then(data => {
            if (!data) return;
            console.log(data);
            renderTable(data.content);
            renderPagination(data.number, data.totalPages, data.last);
        })
        .catch(err => {
            if (err.message !== '로그인 필요') {
                alert('유저 데이터를 불러오는데 실패했습니다.');
//                console.error(err);
            }
        });
}
