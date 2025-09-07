<template>

  <div class="empty-section">
    <img class="img" src="../../assets/book.jpg" alt="livro">
    <p>Pronto para organizar seu dia?</p>
    <p><small>Comece criando sua rotina personalizada para aumentar sua produtividade!</small></p>
    <article class="empty-help">
      <FontAwesomeIcon class="help-icon" icon="bullseye" />
      <div class="empty-help-text">
        <p><strong>Crie sua primeira rotina</strong></p>
        <p>Organize suas atividades do dia</p>
      </div>
    </article>
  </div>

  <button @click="() => $router.push('/routine')" class="btn">
    <FontAwesomeIcon icon="plus" />
    Escolher Rotina
  </button>


</template>

<style scoped>
.btn {
  color: white;
  font-size: smaller;
  background-color: oklch(.623 .214 259.815);
  padding: 8px;
  border-radius: 16px;
  max-width: 200px;
}

.img {
  width: 150px;
  aspect-ratio: 1 / 1;
  object-position: -30px center;
  object-fit: cover;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.5);
}

.empty-help {
  display: flex;
  font-size: small;
  align-items: center;
  padding: 16px;
  border: solid 1px oklch(.809 .105 251.813);
  gap: 8px;
  border-radius: 8px;
  background-color: oklch(.97 .014 254.604);
}

.help-icon {
  background-color: oklch(.546 .245 262.881);
  color: white;
  padding: 8px;
  height: 15px;
  width: 15px;
  border-radius: 999px;
}

.empty-help-text {
  display: flex;
  flex-direction: column;
  align-items: start;
  gap: 4px;
}

.empty-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}
</style>