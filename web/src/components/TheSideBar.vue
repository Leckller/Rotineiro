<script lang="ts">
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { defineComponent } from "vue";
import TheButton from "./Forms/TheButton.vue";

export default defineComponent({
  name: "TheSideBar",
  components: {
    FontAwesomeIcon,
    TheButton,
  },
});
</script>

<template>
  <aside id="barra-lateral">
    <article id="cabecalho">
      <span class="logo"> R+ </span>
      <div class="cabecalho-titulo">
        <p>Rotineiro</p>
        <small>Organize seu dia</small>
      </div>
    </article>

    <nav id="opcoes-nav">
      <button
        @click="() => $router.push('/home')"
        :class="$route.fullPath.includes('home') ? 'selecionado' : ''"
        class="opcao"
      >
        <FontAwesomeIcon icon="calendar" />
        Hoje
      </button>
      <button
        @click="() => $router.push('/routine')"
        :class="$route.fullPath.includes('routine') ? 'selecionado' : ''"
        class="opcao"
      >
        <FontAwesomeIcon icon="list" />
        Rotinas
      </button>
      <button
        @click="() => $router.push('/dashboard')"
        :class="$route.fullPath.includes('dashboard') ? 'selecionado' : ''"
        class="opcao"
      >
        <FontAwesomeIcon icon="chart-line" />
        Dashboard
      </button>
    </nav>

    <section id="perfil">
      <!-- <article class="conteudo-info-perfil">
        <span class="icone-perfil"> 7 </span>
        <div class="info-perfil">
          <p>7leckler</p>
          <small> 7leckler@gmail.com </small>
        </div>
      </article> -->
      <TheButton
        align="start"
        size="full"
        variant="secondary"
        @click="() => $router.push('/')"
      >
        <FontAwesomeIcon icon="arrow-left" />
        Sair
      </TheButton>
    </section>
  </aside>
</template>

<style scoped>
#barra-lateral {
  width: 220px;
  display: flex;
  flex-direction: column;
  gap: var(--gap-base);
  height: 100%;
  border-right: solid 1px var(--light-gray);
}

#opcoes-nav {
  flex-grow: 1;
  gap: var(--gap-small);
  padding: var(--padding-base);
  display: flex;
  flex-direction: column;
  align-items: start;
}

#perfil {
  padding: var(--padding-base);
  display: flex;
  flex-direction: column;
  gap: var(--gap-base);
  border-top: solid 1px var(--light-gray);
}

#cabecalho {
  display: flex;
  gap: var(--gap-base);
  padding: var(--padding-base);
  border-bottom: solid 1px var(--light-gray);
  align-items: center;
}

@media (max-width: 900px) {
  #barra-lateral {
    display: none;
  }
}

.logo {
  background: linear-gradient(to right, #60b5ff, #0dc046);
  width: 40px;
  height: 40px;
  display: flex;
  font-size: var(--medium);
  font-weight: 700;
  color: white;
  border-radius: var(--gap-small);
  justify-content: center;
  align-items: center;
}

.cabecalho-titulo {
  display: flex;
  flex-direction: column;
  align-items: start;
  white-space: nowrap;
}

.cabecalho-titulo p {
  font-weight: 700;
  font-size: var(--medium);
}

.selecionado {
  background-color: var(--medium-2-blue) !important;
  color: var(--white);
}

.opcao {
  padding: var(--padding-base);
  display: flex;
  gap: var(--gap-small);
  align-items: center;
  width: 100%;
  border-radius: var(--padding-small);
}

.opcao:hover {
  background-color: var(--light-gray);
}

.icone-perfil {
  display: flex;
  width: 40px;
  align-items: center;
  justify-content: center;
  color: var(--strong-blue);
  background-color: var(--light-blue);
  height: 40px;
  border-radius: 999px;
}

.info-perfil {
  display: flex;
  flex-direction: column;
  justify-content: start;
}
.conteudo-info-perfil {
  display: flex;
  gap: var(--gap-base);
}
</style>