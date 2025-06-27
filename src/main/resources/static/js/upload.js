let currentPage = 0;
const rowsPerPage = 10;
let tableData = { headers: [], rows: [] };
const pageSize = 10;
window.onload = async () => {
  try {
    const res = await fetch('/user/me', {
      method: 'GET',
      credentials: 'include'
    });

    if (res.status === 401) {
      alert('Você precisa estar logado');
      window.location.href = 'login';
      return;
    }

    if (!res.ok) {
      alert('Erro inesperado');
      return;
    }

    const text = await res.text();


  } catch (err) {
    alert('Erro ao verificar autenticação');
    window.location.href = 'login';
  }
};


async function listarPessoas() {
    try {
        const res = await fetch('persons/list', {
            method: 'GET',
            credentials: 'include'
        });

        if (!res.ok) throw new Error('Erro ao buscar os dados: ' + res.statusText);

        const csvText = await res.text();
        const linhas = csvText.trim().split('\n');

        tableData.headers = linhas[0].split(',');
        tableData.rows = linhas.slice(1).map(linha => linha.split(','));

        currentPage = 1;
        mostrarPagina(currentPage);

    } catch (err) {
        alert(err.message);
    }
}

function mostrarPagina(page) {
    const container = document.getElementById('resultado');
    container.innerHTML = '';

    const tabela = document.createElement('table');

    // Cabeçalho
    const thead = tabela.createTHead();
    const rowHead = thead.insertRow();
    tableData.headers.forEach(h => {
        const th = document.createElement('th');
        th.textContent = h;
        rowHead.appendChild(th);
    });

    // Corpo
    const tbody = tabela.createTBody();

    const start = (page - 1) * rowsPerPage;
    const end = Math.min(start + rowsPerPage, tableData.rows.length);

    for (let i = start; i < end; i++) {
        const row = tbody.insertRow();
        tableData.rows[i].forEach(cell => {
            const td = row.insertCell();
            td.textContent = cell.replace(/"/g, '');
        });
    }

    container.appendChild(tabela);

    // Controles de paginação
    const controls = document.createElement('div');
    controls.id = 'paginationControls';

    const prevButton = document.createElement('button');
    prevButton.textContent = 'Anterior';
    prevButton.disabled = page === 1;
    prevButton.onclick = () => {
        if (currentPage > 1) {
            currentPage--;
            mostrarPagina(currentPage);
        }
    };

    const nextButton = document.createElement('button');
    nextButton.textContent = 'Próximo';
    nextButton.disabled = end >= tableData.rows.length;
    nextButton.onclick = () => {
        if (end < tableData.rows.length) {
            currentPage++;
            mostrarPagina(currentPage);
        }
    };

    controls.appendChild(prevButton);
    controls.appendChild(nextButton);
    container.appendChild(controls);
}



async function uploadFile() {
    const fileInput = document.getElementById('fileInput');
    if (fileInput.files.length === 0) {
        alert('Selecione um arquivo CSV');
        return;
    }

    const formData = new FormData();
    formData.append('file', fileInput.files[0]);

    try {
        const res = await fetch('/persons/upload', {
              method: 'POST',
              body: formData,
              credentials: 'include'
        });

        if (!res.ok) {
            throw new Error('Erro ao enviar arquivo: ' + res.statusText);
        }

        const data = await res.json();

        let html = `<p><strong>${data.message}</strong></p>`;
        html += `<p>Total de registros: ${data.totalRegistros}</p>`;

        html += `<p><strong>Contagem por Sexo:</strong></p><ul>`;
        for (const sexo in data.contagemPorSexo) {
            html += `<li>${sexo}: ${data.contagemPorSexo[sexo]}</li>`;
        }
        html += `</ul>`;

        html += `<p><strong>Média de idade por Sexo:</strong></p><ul>`;
        for (const sexo in data.mediaIdadePorSexo) {
            html += `<li>${sexo}: ${data.mediaIdadePorSexo[sexo].toFixed(2)}</li>`;
        }
        html += `</ul>`;

        document.getElementById('uploadInfo').innerHTML = html;

    } catch (err) {
        alert(err.message);
    }

}

 function exportarCSV() {
        if (tableData.rows.length === 0) {
            alert('Nenhum dado para exportar.');
            return;
        }

        let csvContent = tableData.headers.join(',') + '\n';

        tableData.rows.forEach(row => {
            csvContent += row.join(',') + '\n';
        });

        const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
        const url = URL.createObjectURL(blob);

        const link = document.createElement('a');
        link.setAttribute('href', url);
        link.setAttribute('download', 'pessoas_exportadas.csv');
        link.style.display = 'none';

        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
 }

async function carregarPagina(page) {
    try {
         const response = await fetch(`/persons/page?page=${page}&size=10`, { credentials: 'include' });
               if (!response.ok) throw new Error('Erro ao carregar página');

               const data = await response.json();

        const tbody = document.getElementById('tabela-corpo');
        tbody.innerHTML = ''; // limpa tabela

        data.content.forEach(p => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${p.nome}</td>
                <td>${p.sobreNome}</td>
                <td>${p.email}</td>
                <td>${p.sexo}</td>
                <td>${p.ipAcesso}</td>
                <td>${p.idade}</td>
                <td>${p.nascimento}</td>
            `;
            tbody.appendChild(tr);
        });

        // Atualiza botões de paginação
        const pagDiv = document.getElementById('paginacao');
        pagDiv.innerHTML = `
            <button onclick="carregarPagina(${data.number - 1})" ${data.first ? 'disabled' : ''}>Anterior</button>
            <span>Página ${data.number + 1} de ${data.totalPages}</span>
            <button onclick="carregarPagina(${data.number + 1})" ${data.last ? 'disabled' : ''}>Próximo</button>
        `;

        currentPage = data.number;

    } catch (error) {
        alert(error.message);
    }
}
window.onload = () => carregarPagina(0);


