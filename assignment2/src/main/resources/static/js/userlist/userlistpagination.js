// userlistPagination.js
export function createUserListPagination(onPageChange) {
    const wrapper = document.createElement("div");
    wrapper.style.marginTop = "16px";
    wrapper.style.textAlign = "center";

    function render(current, totalPages, isLast) {
        wrapper.innerHTML = "";
        if (totalPages <= 1) return;

        const pageSize = 10;
        const currentGroup = Math.floor(current / pageSize);
        const startPage = currentGroup * pageSize;
        let endPage = startPage + pageSize - 1;
        if (endPage >= totalPages) endPage = totalPages - 1;

        if (startPage > 0) {
            const prevBtn = document.createElement("button");
            prevBtn.textContent = "이전";
            prevBtn.onclick = () => onPageChange(startPage - 1);
            wrapper.appendChild(prevBtn);
        }

        for (let i = startPage; i <= endPage; i++) {
            const btn = document.createElement("button");
            btn.textContent = (i + 1);
            btn.disabled = i === current;
            btn.onclick = () => onPageChange(i);
            if (i === current) btn.style.background = "#777c7b";
            wrapper.appendChild(btn);
        }

        if (!isLast && endPage < totalPages - 1) {
            const nextBtn = document.createElement("button");
            nextBtn.textContent = "다음";
            nextBtn.onclick = () => onPageChange(endPage + 1);
            wrapper.appendChild(nextBtn);
        }
    }

    return { wrapper, render };
}
