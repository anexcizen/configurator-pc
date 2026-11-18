document.addEventListener('DOMContentLoaded', function () {

    const deleteForms = document.querySelectorAll('form.form-delete');
    deleteForms.forEach(form => {
        form.addEventListener('submit', function (e) {
            const message = form.getAttribute('data-confirm') || 'Вы уверены, что хотите удалить этот элемент?';
            if (!confirm(message)) {
                e.preventDefault();
            }
        });
    });

    const addSections = document.querySelectorAll('.category-add-section');

    addSections.forEach(section => {
        const toggleBtn = section.querySelector('.btn-toggle-add');
        const cancelBtn = section.querySelector('.btn-cancel-add');
        const form = section.querySelector('.category-add-form');
        const searchInput = section.querySelector('.component-search');
        const selectEl = section.querySelector('.component-select');

        if (!form || !selectEl) return;

        const submitBtn = form.querySelector('button[type="submit"]');
        const allOptions = Array.from(selectEl.options).map(opt => ({
            value: opt.value,
            text: opt.text
        }));

        if (toggleBtn) {
            toggleBtn.addEventListener('click', function () {
                toggleBtn.style.display = 'none';
                form.style.display = 'block';
                if (searchInput) {
                    searchInput.value = '';
                    searchInput.dispatchEvent(new Event('input'));
                    searchInput.focus();
                }
            });
        }

        if (cancelBtn) {
            cancelBtn.addEventListener('click', function () {
                form.style.display = 'none';
                if (toggleBtn) toggleBtn.style.display = 'block';
            });
        }

        if (searchInput) {
            searchInput.addEventListener('keydown', function (e) {
                if (e.key === 'Enter') {
                    e.preventDefault();
                }
            });

            searchInput.addEventListener('input', function () {
                const query = this.value.toLowerCase().trim();
                selectEl.innerHTML = '';

                const matched = allOptions.filter(opt => opt.text.toLowerCase().includes(query));

                if (matched.length === 0) {
                    const emptyOpt = document.createElement('option');
                    emptyOpt.value = '';
                    emptyOpt.textContent = 'Ничего не найдено';
                    emptyOpt.disabled = true;
                    emptyOpt.selected = true;
                    selectEl.appendChild(emptyOpt);
                    if (submitBtn) submitBtn.disabled = true;
                } else {
                    matched.forEach((opt, index) => {
                        const optionEl = document.createElement('option');
                        optionEl.value = opt.value;
                        optionEl.textContent = opt.text;
                        if (index === 0) optionEl.selected = true;
                        selectEl.appendChild(optionEl);
                    });
                    if (submitBtn) submitBtn.disabled = false;
                }
            });
        }
    });

    const categorySelect = document.getElementById('categorySelect');
    const socketField = document.getElementById('socketField');
    const ramTypeField = document.getElementById('ramTypeField');
    const powerWattsField = document.getElementById('powerWattsField');

    if (categorySelect && socketField && ramTypeField && powerWattsField) {
        function toggleField(fieldWrapper, shouldShow) {
            const input = fieldWrapper.querySelector('input');
            if (shouldShow) {
                fieldWrapper.style.display = 'block';
                if (input) input.required = true;
            } else {
                fieldWrapper.style.display = 'none';
                if (input) {
                    input.required = false;
                    input.value = '';
                }
            }
        }

        function updateFieldsByCategory() {
            const selectedOption = categorySelect.options[categorySelect.selectedIndex];
            const categoryName = selectedOption ? selectedOption.text.trim() : '';

            const needSocket = (categoryName === 'Процессор' || categoryName === 'Материнская плата');
            const needRam = (categoryName === 'Материнская плата' || categoryName === 'Оперативная память');
            const needPower = (categoryName === 'Процессор' || categoryName === 'Видеокарта' || categoryName === 'Блок питания');

            toggleField(socketField, needSocket);
            toggleField(ramTypeField, needRam);
            toggleField(powerWattsField, needPower);
        }

        categorySelect.addEventListener('change', updateFieldsByCategory);
        updateFieldsByCategory();
    }

});