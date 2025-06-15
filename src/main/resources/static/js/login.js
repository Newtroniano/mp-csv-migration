 document.getElementById('loginForm').addEventListener('submit', async (e) => {
      e.preventDefault();

      const res = await fetch('login', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          user: document.getElementById('username').value,
          password: document.getElementById('password').value
        })
      });

      if (res.ok) {
        const data = await res.json();
        localStorage.setItem('token', data.token);

        window.location.href = 'upload';
      } else {
        alert('Login inválido');
      }
    });