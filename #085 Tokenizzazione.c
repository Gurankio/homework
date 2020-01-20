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
#include <string.h>

#define LENGTH 64

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int token(char input[LENGTH], char output[LENGTH][LENGTH]);

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
  int count = 0;
  char *temp = strtok(input, " ");

  while (temp != NULL) {
    strcpy(output[count++], temp);
    temp = strtok(NULL, " ");
  }

  return count;
}
