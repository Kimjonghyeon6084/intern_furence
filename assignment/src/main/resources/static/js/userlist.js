let currentPage = 0;
const size = 10;
let lastSearchParams = null;


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
        fetchUserList(page, lastSearchParams);
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
    console.log("users : ", users)
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

// 페이지 버튼 만드는 부분
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
        pageDiv.innerHTML += `<button onclick="movePage(${startPage - 1})"
                                      id="movepagebutton">이전</button>`;
    }

    for (let i = startPage; i <= endPage; i++) {
        pageDiv.innerHTML += `
            <button onclick="movePage(${i})" ${i === current
                                                    ? 'style="background:#777c7b;"'
                                                    : ''} class="commonButton">
                ${i + 1}
            </button>
        `;
    }

    if (!isLast && endPage < totalPages - 1) {
        pageDiv.innerHTML += `<button onclick="movePage(${endPage + 1})"
                                      id="movepagebutton">다음</button>`;
    }
}

// 페이지 로딩될 때 /user/list/{page} 로 get요청
//window.addEventListener('DOMContentLoaded', () => {
//    const pathParts = window.location.pathname.split('/');
//    let page = 0;
//    if (pathParts.length >= 3 && pathParts[1] === 'userlist') {
//        page = parseInt(pathParts[2]) || 0;
//    }
//    currentPage = page;
//    fetchUserList(currentPage);
//});

// 실제 데이터 가져오는 함수
    function fetchUserList(page = 0, params = null) {
      let url = `/user/list/${page}`;
      if (params && params.toString()) {
        url += "?" + params.toString();
        console.log("url",url)
      }
      fetch(url)
        .then(res => {
          if (!res.ok) {
            throw res;
          }
          return res.json();
        })
        .then(data => {
        console.log("data", data)
          renderTable(data.content);
          renderPagination(data.number, data.totalPages, data.last);
        })
        .catch(err => {
          console.error(err);
          alert("데이터 로드에 실패했습니다.");
        });
    }


document.getElementById("userselectlist").addEventListener("submit", function(e) {

    e.preventDefault();

    const id      = document.getElementById("id").value;
    const name    = document.getElementById("name").value;
    const level   = document.getElementById("level").value;
    const desc    = document.getElementById("desc").value;
    const regDate = document.getElementById("regDate").value;
    console.log("id", id)
    console.log("name", name)
    console.log("level", level)
    console.log("desc", desc)
    console.log("regDate", regDate)

    // 값을 아무것도 넣지 않은 상태에서 입력시 alert창 띄워주게 함.
    if (id || name || level || desc || regDate) {
        const params = new URLSearchParams();
                if (id) {
                    params.append("id", id);
                }
                if (name) {
                    params.append("name", name);
                }
                if (level) {
                    params.append("level", level);
                }
                if (desc) {
                    params.append("desc", desc);
                }
                if (regDate) {
                    params.append("regDate", regDate);
                }

            lastSearchParams = params;
            fetchUserList(0, params);

            // 페이징용 페이지 이동
            function movePage(page) {
              fetchUserList(page, lastSearchParams);
            }
    } else {
        alert("조건을 입력하세요.")
    }
});

