document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    try {
      const res = await fetch('/user/login', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          user: document.getElementById('username').value,
          password: document.getElementById('password').value
        }),
        credentials: 'include'
      });

      if (res.ok) {
        window.location.href = '/upload';
      } else {
        alert('Login inválido');
      }
    } catch(err) {
      console.error(err);
      alert('Erro na requisição');
    }
  });
});
