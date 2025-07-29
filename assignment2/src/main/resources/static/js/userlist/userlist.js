import { createUserListSearchForm } from "./userlistSearchForm.js";
import { createUserListGrid } from "./userlistGrid.js";
import { createUserListPagination } from "./userlistPagination.js";

export function createUserListView() {
    const wrapper = document.createElement("div");
    wrapper.style.padding = "20px";

    let lastSearchParams = null;
    let currentPage = 0;

    // 데이터 로드/검색
        function fetchUserList(page = 0, params = null) {
            let url = `/user/list/${page}`;
            if (params) {
                const search = new URLSearchParams(params);
                if (search.toString()) url += "?" + search.toString();
            }
            fetch(url)
                .then(res => res.json())
                .then(data => {
                    gridObject.setData(data.content || []);
                    paginationObject.render(data.number, data.totalPages, data.last);
                })
                .catch(() => {
                    gridObject.setData([]);
                    paginationObject.render(0, 0, true);
                    alert("데이터 로드 실패");
                });
        }

    function onSearch(params) {
            lastSearchParams = params;
            currentPage = 0;
            fetchUserList(currentPage, params);
        }
        function onReset() {
            lastSearchParams = null;
            currentPage = 0;
            gridObject.setData([]);
            paginationObject.render(0, 0, true);
        }
        function onPageChange(page) {
            currentPage = page;
            fetchUserList(page, lastSearchParams);
        }

    // 컴포넌트 생성
    const searchFormDiv = createUserListSearchForm(onSearch, onReset);
    const gridObject = createUserListGrid();
    const paginationObject = createUserListPagination(onPageChange);

    wrapper.appendChild(searchFormDiv);
    wrapper.appendChild(gridObject.gridDiv);
    wrapper.appendChild(paginationObject.wrapper);

    // 통합 뷰 반환
    return wrapper;
}
