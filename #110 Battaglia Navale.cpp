/*
 * Jacopo Del Granchio
 * #110 06.05.2020
 *
 * Battaglia Navale
 */

#include <iostream>
#include <fstream>
#include <vector>

// Macro
#define MAP_PATH "./data/#110/navi.txt"
#define SHOTS_PATH "./data/#110/colpi.txt"

// Structs
typedef struct {
  char column;
  int row;
} coord;

#define NULL_COORD (coord){ '-', -1 }

bool operator== (const coord &lhs, const coord &rhs) {
  return lhs.column == rhs.column && lhs.row == rhs.row;
}

typedef struct {
  int points;
  bool destroyed[3];
  std::vector<std::vector<int> > updatedMap;
} result;

// Prototipi
bool loadMap(std::vector<std::vector<int> > &map);
bool loadShots(std::vector<coord> &shots);

void displayResult(std::vector<std::vector<int> > map, std::vector<coord> shots);
result getResult(std::vector<std::vector<int> > map, std::vector<coord> shots);

void findShip(std::vector<std::vector<int> > map);
void getShips(std::vector<std::vector<int> > map, coord ships[3][3]);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  std::vector<std::vector<int> > map;
  if (!loadMap(map)) {
    std::cout << "Errore durante il caricamento della Mappa." << std::endl;
    return -1;
  }

  // DEBUG
  // for (auto it = map.begin(); it != map.end(); it++) {
  //   for (auto jt = it->begin(); jt != it->end(); jt++) {
  //     std::cout << *jt << ' ';
  //   }
  //   std::cout << std::endl;
  // }

  std::vector<coord> shots;
  if (!loadShots(shots)) {
    std::cout << "Errore durante il caricamento dei Colpi." << std::endl;
    return -1;
  }

  // DEBUG
  // for (auto it = shots.begin(); it != shots.end(); it++) {
  //   std::cout << it->column << it->row << std::endl;
  // }

  int s;

  do {
    std::cout << std::endl;
    std::cout << "   Battaglia Navale" << std::endl;
    std::cout << "1) Risultato" << std::endl;
    std::cout << "2) Cerca nave" << std::endl;
    std::cout << "0) Esci" << std::endl;
    std::cout << "Scelta: ";
    std::cin >> s;
    std::cout << std::endl;

    switch (s) {
      case 1:
        displayResult(map, shots);
        break;

      case 2:
        findShip(map);
        break;

      case 0:
        std::cout << "Arrivederci." << std::endl;
        break;

      default:
        std::cout << "Scelta non valida." << std::endl;
    }
  } while (s != 0);

  return 0;
}

bool loadMap(std::vector<std::vector<int> > &map) {
  std::ifstream input(MAP_PATH);
  if (!input) {
    return false;
  }

  char next = input.peek();
  while (!input.eof()) {
    std::vector<int> vT;
    int t;
    while (next != 13) {
      input >> t;
      vT.push_back(t);
      next = input.peek();
    }
    input.get();
    input.get();

    map.push_back(vT);
    next = input.peek();
  }

  return true;
}

bool loadShots(std::vector<coord> &shots) {
  std::ifstream input(SHOTS_PATH);
  if (!input) {
    return false;
  }

  char next = input.peek();
  while (!input.eof()) {
    coord t;
    while (next != 13) {
      t.column = input.get();
      t.row = (int)(input.get() - '0');
      shots.push_back(t);
      next = input.get();
    }
    input.get();

    next = input.peek();
  }

  return true;
}

void displayResult(std::vector<std::vector<int> > map, std::vector<coord> shots) {
  result r = getResult(map, shots);

  for (auto it = r.updatedMap.begin(); it != r.updatedMap.end(); it++) {
    for (auto jt = it->begin(); jt != it->end(); jt++) {
      if (*jt < 0) {
        std::cout << "* ";
      } else {
        std::cout << *jt << ' ';
      }
    }
    std::cout << std::endl;
  }

  std::cout << std::endl;

  for (int i=0; i<3; i++) {
    if (r.destroyed[i]) {
      std::cout << "Affondata la nave da " << i+1 << '!' << std::endl;
    }
  }

  std::cout << "Punti ottenuti: " << r.points << std::endl;
}

result getResult(std::vector<std::vector<int> > map, std::vector<coord> shots) {
  result r = { 0, {true, true, true}, map };

  coord ships[3][3];
  getShips(map, ships);

  for (auto it = shots.begin(); it != shots.end(); it++) {
    for (int i=0; i<3; i++) {
      for (int j=0; j<=i; j++) {
        if (*it == ships[i][j]) {
          r.points++;
          r.updatedMap.at(ships[i][j].row - 1).at(ships[i][j].column - 'a') = -1;
          ships[i][j] = NULL_COORD;
        }
      }
    }
  }

  for (int i=0; i<3; i++) {
    for (int j=0; j<=i; j++) {
      if (ships[i][j] == NULL_COORD) r.destroyed[i] = false;
    }

    if (r.destroyed[i]) r.points++;
  }

  return r;
}

void findShip(std::vector<std::vector<int> > map) {
  coord ships[3][3];
  getShips(map, ships);

  int s;
  do {
    std::cout << "Lunghezza nave: ";
    std::cin >> s;
    if (s < 1 || s > 3) std::cout << "Lunghezza non valida." << std::endl;
  } while (s < 1 || s > 3);
  s--;

  for (int j=0; j <= s; j++) {
    std::cout << ships[s][j].column << ships[s][j].row << std::endl;
  }
}

void getShips(std::vector<std::vector<int> > map, coord ships[3][3]) {
  for (int i=0; i<3; i++) {
    for (int j=0; j<=i; j++) {
      ships[i][j] = NULL_COORD;
    }
  }

  for (auto it = map.begin(); it != map.end(); it++) {
    for (auto jt = it->begin(); jt != it->end(); jt++) {
      switch (*jt) {
        case 1:
          if (ships[0][0] == NULL_COORD) ships[0][0] = { (char)(std::distance(it->begin(), jt) + 'a'), (int)(std::distance(map.begin(), it) + 1) };
          break;

        case 2:
          if (ships[1][0] == NULL_COORD) ships[1][0] = { (char)(std::distance(it->begin(), jt) + 'a'), (int)(std::distance(map.begin(), it) + 1) };
          else if (ships[1][1] == NULL_COORD) ships[1][1] = { (char)(std::distance(it->begin(), jt) + 'a'), (int)(std::distance(map.begin(), it) + 1) };
          break;

        case 3:
          if (ships[2][0] == NULL_COORD) ships[2][0] = { (char)(std::distance(it->begin(), jt) + 'a'), (int)(std::distance(map.begin(), it) + 1) };
          else if (ships[2][1] == NULL_COORD) ships[2][1] = { (char)(std::distance(it->begin(), jt) + 'a'), (int)(std::distance(map.begin(), it) + 1) };
          else if (ships[2][2] == NULL_COORD) ships[2][2] = { (char)(std::distance(it->begin(), jt) + 'a'), (int)(std::distance(map.begin(), it) + 1) };
          break;

        case 0:
        default:
          break;
      }
    }
  }
}
