 document.getElementById('loginForm').addEventListener('submit', async (e) => {
      e.preventDefault();

      const res = await fetch('user/login', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          user: document.getElementById('username').value,
          password: document.getElementById('password').value
        })
      });

      if (res.ok) {
        const response = await res.json();
        localStorage.setItem('token', response.data.token);
        window.location.href = 'upload';
      } else {
        alert('Login inválido');
      }
    });

function voltar(){window.location.href = '/';}

    