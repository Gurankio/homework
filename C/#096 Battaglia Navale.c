/*
 * Jacopo Del Granchio
 * #096 19.02.2020
 *
 * 0 -> Acqua.
 * 1 -> Sommergibile.
 * 2 -> Acqua colpita.
 * 3 -> Sommergibile affondato.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <time.h>

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
void generaGriglia(int[5][3]);
void aggiorna(int[5][3], int, int);
int vinto(int[5][3]);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int griglia[5][3];
  generaGriglia(griglia);

  int ris;

  for (int k = 0; k < 10; k++) {
    putchar('\n');

    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 3; j++)
        printf("%d  ", griglia[i][j] != 1 ? griglia[i][j] : 0);

      putchar('\n');
    }

    int c, r;
    printf("Inserire la coordinata X: ");
    scanf("%d", &c);

    printf("Inserire la coordinata Y: ");
    scanf("%d", &r);

    aggiorna(griglia, c, r);

    if ((ris = vinto(griglia)) == 1) break;
  }

  putchar('\n');

  if (ris) printf("Hai vinto.\n");
  else printf("Hai perso.\n");

  putchar('\n');

  for (int i = 0; i < 5; i++) {
    for (int j = 0; j < 3; j++)
      printf("%d  ", griglia[i][j]);

    putchar('\n');
  }

  // getchar();
  // system("pause");
  return 0;
}

void generaGriglia(int griglia[5][3]) {
  for (int i = 0; i < 5; i++)
    for (int j = 0; j < 3; j++)
      griglia[i][j] = 0;

  srand((unsigned)time(NULL) + rand());

  int r, c;

  for (int i = 0; i < 2; i++) {
    do r = rand() % 5, c = rand() % 3;
    while (griglia[r][c] == 1);

    griglia[r][c] = 1;
  }
}

void aggiorna(int griglia[5][3], int c, int r) {
  switch (griglia[r][c]) {
    case 0:
      printf("Acqua colpita.\n");
      griglia[r][c] = 2;
      break;

    case 1:
      printf("Sommergibile affondato.\n");
      griglia[r][c] = 3;
      break;

    case 2:
      printf("Acqua già colpita.\n");
      break;

    case 3:
      printf("Sommergibile gia affondato.\n");
      break;
  }
}

int vinto(int griglia[5][3]) {
  int vinto = 1;

  for (int i = 0; i < 5; i++)
    for (int j = 0; j < 3; j++)
      if (griglia[i][j] == 1) vinto = 0;

  return vinto;
}
