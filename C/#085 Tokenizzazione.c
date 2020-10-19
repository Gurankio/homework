/*
 * Jacopo Del Granchio
 * #085 20.01.2020
 *
 * Tokenizza una stringa in input.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>

#define LENGTH 64

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int token(char input[LENGTH], char output[LENGTH][LENGTH]);
int len(char *input);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  char input[LENGTH];
  printf("Inserire una stringa: ");
  gets(input);

  char output[LENGTH][LENGTH];
  int i, count = token(input, output);

  printf("Output:\n");

  for (i = 0; i < count; i++)
    printf("\t%-3d) %s\n", i + 1, output[i]);

  // getchar();
  // system("pause");
  return 0;
}

int token(char input[LENGTH], char output[LENGTH][LENGTH]) {
  for (int i = 0; i < LENGTH; i++)
    for (int j = 0; j < LENGTH; j++)
      output[i][j] = '\0';

  int countOut = 0;

  for (int i = 0; i < len(input) + 1; i++) {
    int j;

    for (j = i; j < len(input) + 1; j++) {
      if (input[j] == ' ' || input[j] == '\0') {
        int a;

        if (j - i == 0) break;

        for (a = 0; a < (j - i); a++)
          output[countOut][a] = input[i + a];

        countOut++;
        break;
      }
    }

    i = j;
  }

  return countOut;
}

int len(char *input) {
  int i = 0;

  while (input[i] != '\0') i++;

  return i;
}
