/*
 * Jacopo Del Granchio
 * #103-1 29.02.2020
 *
 * Primo esercizio sui file.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <string.h>

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi

#define N 6
#define LS 32

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  FILE *txt = fopen("data/#103/a.txt", "w");
  if (txt == NULL) {
    printf("Errore di I/O.\n");
    return 0;
  }

  char str[N][LS] = {"Qwerty", "qWerty", "qwErty", "qweRty", "qwerTy", "qwertY"};
  for (int i=0; i<N; i++) {
    if (fputs(str[i], txt) == EOF) printf("Errore di I/O.\n");
    fputc('\n', txt);
  }

  fclose(txt);

  //

  txt = fopen("data/#103/a.txt", "a");
  if (txt == NULL) {
    printf("Errore di I/O.\n");
    return 0;
  }

  char buffer[LS];
  printf("Inserire una stringa: ");
  gets(buffer);

  if (fputs(buffer, txt) == EOF) printf("Errore di I/O.\n");
  fputc('\n', txt);

  fclose(txt);

  //

  txt = fopen("data/#103/a.txt", "r");

  char inputBuffer[LS];
  while (fgets(inputBuffer, LS, txt) != NULL) {
    printf("%s", inputBuffer);
  }

  fclose(txt);

  // getchar();
  // system("pause");
  return 0;
}
