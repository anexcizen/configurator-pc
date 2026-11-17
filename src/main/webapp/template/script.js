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
});