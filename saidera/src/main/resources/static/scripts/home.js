document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('createBillForm');
    const nameInput = document.getElementById('creatorName');

    form.addEventListener('submit', async function(e) {
        e.preventDefault();

        const name = nameInput.value.trim();

        if (!name) {
            alert('Por favor, insira seu nome.');
            return;
        }

        try {
            // Criar uma nova Bill com o creatorName
            const billResponse = await fetch('/bills', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    creatorName: name,
                    isActive: true
                })
            });

            if (!billResponse.ok) {
                throw new Error('Erro ao criar comanda');
            }

            const bill = await billResponse.json();

            // Adicionar o criador como pessoa na Bill
            const peopleResponse = await fetch(`/bills/${bill.id}/people`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name: name
                })
            });

            if (!peopleResponse.ok) {
                throw new Error('Erro ao adicionar pessoa');
            }

            // Redirecionar para a página da comanda
            window.location.href = `/bill/${bill.id}`;

        } catch (error) {
            console.error('Erro:', error);
            alert('Ocorreu um erro ao criar a comanda. Por favor, tente novamente.');
        }
    });

    // Validação do campo de nome
    nameInput.addEventListener('input', function() {
        this.value = this.value.replace(/[0-9]/g, '');
        if (this.value.length > 50) {
            this.value = this.value.substring(0, 50);
        }
    });
});