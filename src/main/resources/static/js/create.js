document.getElementById("cadastro-form").addEventListener("submit", async function(event) {
  event.preventDefault();

  const user = document.getElementById("user").value;
  const password = document.getElementById("password").value;

  try {
    const response = await fetch("user", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ user, password })
    });

    if (response.ok) {
      alert("Usuário cadastrado com sucesso! Clique em OK para fazer o login");
      window.location.href ='login'
    } else {
      const error = await response.text();
      alert("Erro ao cadastrar: " + error);
    }
  } catch (err) {
    console.error("Erro ao enviar dados:", err);
    alert("Erro de conexão. Tente novamente.");
  }
});

