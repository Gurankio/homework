/*
 * Jacopo Del Granchio
 * #108 25.03.2020
 * Bibioteca.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <strings.h>
#include <ctype.h>

#define NL 100
#define LS 32

typedef struct libro {
  int codiceLibro;           // Codice del libro
  char titolo[LS];           // Titolo del libro
  char autore[LS];           // Autore del libro (cognome)
  char casaEditrice[LS];     // Casa editrice del libro (Mondadori, Feltrinelli...)
  char genere[LS];           // genere del libro (horror, sci-fi...)
  int annoPubblicazione;     // Anno della prima edizione del libro
  float prezzo;              // Prezzo in euro
} libro;

// Prototipi
// Menu 0
void menu(libro[], int *);
void aggiungiLibro(libro[], int *);
void stampaCatalogo(libro[], int *);

// Menu 1
void menuRicerca(libro[], int *);
int ricercaTitolo(libro[], int);
int ricercaAutore(libro[], int);
int ricercaAnnoPubblicazione(libro[], int);
int ricercaGenere(libro[], int);

// Menu 2
void menuLibro(libro[], int *, int);
void modificaLibro(libro[], int *, int);
void eliminaLibro(libro[], int *, int);

// Menu 3
void menuOrdina(libro[], int *);
void ordinaAutore(libro[], int);
void ordinaLibriPubblicati(libro[], int);

// I/O
void carica(libro[], int *);
void salva(libro[], int);

void inputPulito(char *, char *);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  libro dati[NL];
  int n = 0;

  carica(dati, &n);
  menu(dati, &n);
  return 0;
}

void menu(libro dati[], int *n) {
  int s;
  char buffer[LS];

  void (*funzioni[4])(libro[], int*) = {aggiungiLibro, stampaCatalogo, menuRicerca, menuOrdina};

  do {
    putchar('\n');
    printf("   Menu Bibloteca\n");
    printf("1) Aggiungi libro\n");
    printf("2) Stampa catalogo\n");
    printf("3) Ricerca...\n");
    printf("4) Ordina...\n");
    printf("0) Esci\n");
    printf("\nInput: ");
    fgets(buffer, LS, stdin);
    s = atoi(buffer);

    if (s < 0 || s > 4) printf("Scelta non valida.\n");
    else if (s != 0) funzioni[s - 1](dati, n);
  } while (s != 0);
}

void aggiungiLibro(libro dati[], int *n) {
  if (*n == NL) {
    printf("Spazio esaurito.\n");
    return;
  }

  inputPulito("Inserire il titolo: ", dati[*n].titolo);
  inputPulito("Inserire l'autore: ", dati[*n].autore);
  inputPulito("Inserire la casa editrice: ", dati[*n].casaEditrice);
  inputPulito("Inserire il genere: ", dati[*n].genere);

  char buffer[LS];

  printf("Inserire anno di pubblicazione: ");
  fgets(buffer, LS, stdin);
  dati[*n].annoPubblicazione = atoi(buffer);

  printf("Inserire il prezzo: ");
  fgets(buffer, LS, stdin);
  dati[*n].prezzo = atof(buffer);

  // Cerca il codice.
  int max = 0;

  for (int i = 0; i < *n; i++)
    if (dati[i].codiceLibro > max) max = dati[i].codiceLibro;

  dati[*n].codiceLibro = max + 1;
  printf("Il codice del nuovo libro e' %d.\n", max + 1);

  (*n)++;
  salva(dati, *n);
}

void stampaCatalogo(libro dati[], int *n) {
  if (*n == 0) {
    printf("Catalogo vuoto.\n");
  }

  printf("\nCodice %-*s %-*s %-*s %-*s %s %s\n", LS, "Titolo", LS, "Autore", LS, "Casa Editrice", LS, "Genere", "Anno di Pubblicazione", "Prezzo");

  for (int i = 0; i < *n; i++) {
    printf("#%d %-*s %-*s %-*s %-*s %-6d %.2f\n",
           dati[i].codiceLibro,
           LS, dati[i].titolo,
           LS, dati[i].autore,
           LS, dati[i].casaEditrice,
           LS, dati[i].genere,
           dati[i].annoPubblicazione,
           dati[i].prezzo);
  }
}

void menuRicerca(libro dati[], int *n) {
  int s;
  char buffer[LS];

  int (*funzioni[4])(libro[], int) = {ricercaTitolo, ricercaAutore, ricercaAnnoPubblicazione, ricercaGenere};

  do {
    putchar('\n');
    printf("   Menu Ricerca\n");
    printf("1) Ricerca libro per Titolo\n");
    printf("2) Ricerca libro per Autore\n");
    printf("3) Ricerca libro per Anno di Pubblicazione\n");
    printf("4) Ricerca libro per Genere\n");
    printf("0) Esci\n");
    printf("\nInput: ");
    fgets(buffer, LS, stdin);
    s = atoi(buffer);

    if (s < 0 || s > 4) printf("Scelta non valida.\n");
    else if (s != 0) {
      int r = funzioni[s - 1](dati, *n);
      if (r != -1) {
        printf("\nDati del libro selezionato: \n");
        printf("\tCodice: #%d\n", dati[r].codiceLibro);
        printf("\tTitolo: %s\n", dati[r].titolo);
        printf("\tAutore: %s\n", dati[r].autore);
        printf("\tCasa Editrice: %s\n", dati[r].casaEditrice);
        printf("\tGenere: %s\n", dati[r].genere);
        printf("\tAnno di Pubblicazione: %d\n", dati[r].annoPubblicazione);
        printf("\tPrezzo: %.2f\n", dati[r].prezzo);
        menuLibro(dati, n, r);
      }
    }
  } while (s != 0);
}

void menuOrdina(libro dati[], int *n) {
  int s;
  char buffer[LS];

  void (*funzioni[2])(libro[], int) = {ordinaAutore, ordinaLibriPubblicati};

  do {
    putchar('\n');
    printf("   Menu Ordinamento\n");
    printf("1) Ordina per Autore\n");
    printf("2) Ordina per Libri Pubblicati\n");
    printf("0) Esci\n");
    printf("\nInput: ");
    fgets(buffer, LS, stdin);
    s = atoi(buffer);

    if (s < 0 || s > 2) printf("Scelta non valida.\n");
    else if (s != 0) funzioni[s - 1](dati, *n);
  } while (s != 0 && s != 1 && s != 2);
}

int ricercaTitolo(libro dati[], int n) {
  char buffer[LS];
  inputPulito("Inserire il titolo da ricercare: ", buffer);

  int foundIndex[n], foundN = 0;
  for (int i=0; i<n; i++) {
    if (strstr(dati[i].titolo, buffer) != NULL) {
      foundIndex[foundN++] = i;
    }
  }

  printf("%d risultat%c trovati.\n", foundN, foundN == 1 ? 'o' : 'i');
  for (int i=0; i<foundN; i++) {
    printf("%d) %-*s %-*s %-*s\n", i+1, LS, dati[i].titolo, LS, dati[i].autore, LS, dati[i].genere);
  }
  printf("0) Esci\n");

  int s;
  do {
    printf("\nInput: ");
    fgets(buffer, LS, stdin);
    s = atoi(buffer);
    if (s < 0 || s > foundN) printf("Scelta non valida.\n");
  } while (s < 0 || s > foundN);

  if (s != 0) {
    return foundIndex[s];
  }
  return -1;
}

int ricercaAutore(libro dati[], int n) {
  char buffer[LS];
  inputPulito("Inserire l'autore da ricercare: ", buffer);

  int foundIndex[n], foundN = 0;
  for (int i=0; i<n; i++) {
    if (strstr(dati[i].autore, buffer) != NULL) {
      foundIndex[foundN++] = i;
    }
  }

  printf("%d risultat%c trovati.\n", foundN, foundN == 1 ? 'o' : 'i');
  for (int i=0; i<foundN; i++) {
    printf("%d) %-*s %-*s %-*s\n", i+1, LS, dati[i].titolo, LS, dati[i].autore, LS, dati[i].genere);
  }
  printf("0) Esci\n");

  int s;
  do {
    printf("\nInput: ");
    fgets(buffer, LS, stdin);
    s = atoi(buffer);
    if (s < 0 || s > foundN) printf("Scelta non valida.\n");
  } while (s < 0 || s > foundN);

  if (s != 0) {
    return foundIndex[s];
  }
  return -1;
}

int ricercaAnnoPubblicazione(libro dati[], int n) {
  char buffer[LS];
  inputPulito("Inserire l'autore da ricercare: ", buffer);
  int anno = atoi(buffer);

  int foundIndex[n], foundN = 0;
  for (int i=0; i<n; i++) {
    if (dati[i].annoPubblicazione == anno) {
      foundIndex[foundN++] = i;
    }
  }

  printf("%d risultat%c trovati.\n", foundN, foundN == 1 ? 'o' : 'i');
  for (int i=0; i<foundN; i++) {
    printf("%d) %-*s %-*s %-6d\n", i+1, LS, dati[i].titolo, LS, dati[i].autore, dati[i].annoPubblicazione);
  }
  printf("0) Esci\n");

  int s;
  do {
    printf("\nInput: ");
    fgets(buffer, LS, stdin);
    s = atoi(buffer);
    if (s < 0 || s > foundN) printf("Scelta non valida.\n");
  } while (s < 0 || s > foundN);

  if (s != 0) {
    return foundIndex[s];
  }
  return -1;
}

int ricercaGenere(libro dati[], int n) {
  char buffer[LS];
  inputPulito("Inserire il genere da ricercare: ", buffer);

  int foundIndex[n], foundN = 0;
  for (int i=0; i<n; i++) {
    if (strstr(dati[i].titolo, buffer) != NULL) {
      foundIndex[foundN++] = i;
    }
  }

  printf("%d risultat%c trovati.\n", foundN, foundN == 1 ? 'o' : 'i');
  for (int i=0; i<foundN; i++) {
    printf("%d) %-*s %-*s %-*s\n", i+1, LS, dati[i].titolo, LS, dati[i].autore, LS, dati[i].genere);
  }
  printf("0) Esci\n");

  int s;
  do {
    printf("\nInput: ");
    fgets(buffer, LS, stdin);
    s = atoi(buffer);
    if (s < 0 || s > foundN) printf("Scelta non valida.\n");
  } while (s < 0 || s > foundN);

  if (s != 0) {
    return foundIndex[s];
  }
  return -1;
}

void menuLibro(libro dati[], int *n, int r) {
  int s;
  char buffer[LS];

  void (*funzioni[2])(libro[], int *, int) = {modificaLibro, eliminaLibro};

  do {
    putchar('\n');
    printf("   Menu Libro\n");
    printf("1) Modifica libro\n");
    printf("2) Elimina libro\n");
    printf("0) Esci\n");
    printf("\nInput: ");
    fgets(buffer, LS, stdin);
    s = atoi(buffer);

    if (s < 0 || s > 2) printf("Scelta non valida.\n");
    else if (s != 0) funzioni[s - 1](dati, n, r);
  } while (s != 0 && s != 2);
}

void modificaLibro(libro dati[], int *n, int r) {
  inputPulito("Inserire il titolo: ", dati[r].titolo);
  inputPulito("Inserire l'autore: ", dati[r].autore);
  inputPulito("Inserire la casa editrice: ", dati[r].casaEditrice);
  inputPulito("Inserire il genere: ", dati[r].genere);

  printf("%s\n", dati[r].titolo);

  char buffer[LS];

  printf("Inserire anno di pubblicazione: ");
  fgets(buffer, LS, stdin);
  dati[r].annoPubblicazione = atoi(buffer);

  printf("Inserire il prezzo: ");
  fgets(buffer, LS, stdin);
  dati[r].prezzo = atof(buffer);

  inputPulito("Confermare modifiche? (S/N) ", buffer);
  if (buffer[0] == 'S' || buffer[0] == 's') {
    salva(dati, *n);
    printf("Modifiche salvate.\n");
  } else printf("Modifiche annullate.\n");
}

void eliminaLibro(libro dati[], int *n, int r) {
  char buffer[LS];
  inputPulito("Confermare eliminazione? (S/N) ", buffer);
  if (buffer[0] == 'S' || buffer[0] == 's') {
    for (int i=r; i<*n; i++) {
      dati[i] = dati[i+1];
    }

    (*n)--;
    salva(dati, *n);
    printf("Libro eliminato.\n");
  } else printf("Libro NON eliminato.\n");
}

void ordinaAutore(libro dati[], int n) {
  for (int i=0; i<n-1; i++) {
    for (int j=i; j<n; j++) {
      if (strcasecmp(dati[i].autore, dati[j].autore) > 0) {
        libro temp = dati[i];
        dati[i] = dati[j];
        dati[j] = temp;
      }
    }
  }
}

void ordinaLibriPubblicati(libro dati[], int n) {
  ordinaAutore(dati, n);

  int numeroLibri[n], numeroAutori = 1;

  for (int i=0; i<n; i++) {
    numeroLibri[i] = 0;
  }

  for (int i=0; i<n; i++) {
    if (i != 0 && strcasecmp(dati[i-1].autore, dati[i].autore) != 0) numeroAutori++;
    numeroLibri[numeroAutori - 1]++;
  }

  for (int i=0; i<numeroAutori; i++) {
    int max = -1, maxIndex = 0;
    for (int j=0; j<numeroAutori; j++) {
      if (numeroLibri[j] > max) {
        max = numeroLibri[j];
        maxIndex = j;
      }
    }

    int k = maxIndex;
    for (int j=0; j<n; j++) {
      if (j != 0 && strcasecmp(dati[j-1].autore, dati[j].autore) != 0) k--;
      if (k == 0) {
        k = j;
        break;
      }
    }

    printf("%d) %-*s -> %d\n", i+1, LS, dati[k].autore, numeroLibri[maxIndex]);
    numeroLibri[maxIndex] = -1;
  }
}

void carica(libro dati[], int *n) {
  FILE *file = fopen("./data/#108/biblioteca.dat", "ab");

  fclose(file);
  file = fopen("./data/#108/biblioteca.dat", "rb");

  if (file == NULL) {
    printf("Errore di I/O.\n");
    return;
  }

  (*n) = 0;
  do fread(&dati[(*n)++], sizeof(libro), 1, file);
  while (feof(file) == 0);
  (*n)--;

  fclose(file);
}

void salva(libro dati[], int n) {
  FILE *file = fopen("./data/#108/biblioteca.dat", "wb");

  fwrite(dati, sizeof(libro), n, file);
  fclose(file);
}

// Rimuove \n, trim da destra, trim da sinistra.
void inputPulito(char *outputString, char *inputBuffer) {
  printf(outputString);
  fgets(inputBuffer, LS, stdin);
  for (int i = 0; i < strlen(inputBuffer); i++)
    if (inputBuffer[i] == '\n') inputBuffer[i] = '\0';
  for (int i = strlen(inputBuffer); isspace(inputBuffer[i]); i--)
    inputBuffer[i] = '\0';
  while (isspace(inputBuffer[0]))
    for (int i = 0; i < strlen(inputBuffer); i++)
      inputBuffer[i] = inputBuffer[i + 1];
}
