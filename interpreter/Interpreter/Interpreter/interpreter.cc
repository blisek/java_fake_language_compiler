/*
 * Kod interpretera maszyny rejestrowej do projektu z JFTT2016
 *
 * Autor: Maciek Gębala
 * http://mgc.im.pwr.wroc.pl/
 * 2016-11-14
*/

#include<iostream>
#include<fstream>

#include<tuple>
#include<vector>
#include<map>
#include<string>

#include<cstdlib> 	// rand()
#include<ctime>

using namespace std;

enum Instructions { GET, PUT, LOAD, STORE, COPY, ADD, SUB, SHR, SHL, INC, DEC, ZERO, JUMP, JZERO, JODD, HALT, ERROR, REM };

//#define DEBUG

#ifdef DEBUG
#define DB(instr) { cout << '[' << lr << "] " instr " " << get<1>(program[lr]) << ": " << r[get<1>(program[lr])] << endl; }
#define DB2(ins, i, j) { cout << '[' << lr << "] " ins " " << i << ' ' << j << endl; }
#define DBREG { cout << "\tREGISTERS[" << r[0] << ", " << r[1] << ", " << r[2] << ", " << r[3] << ", " << r[4] << "]\n"; }
#define MEMPEEK {\
    cout << "\tMEMORY["; \
    for(const auto& p : pam) { cout << p.first << " -> " << p.second << ", "; } \
    cout << "]\n";\
}
#else
#define DB(instr) ;
#define DB2(ins, i, j) ;
#define DBREG
#define MEMPEEK
#endif

int main(int argc, char* argv[])
{
    vector< tuple<Instructions,int,int> > program;
    map<int,long long> pam;

    const int reg=5;
    long long r[reg];
    int lr;

    int k=0;
    long long i;
    Instructions i1;
    int i2, i3;
    string com;
    string comment;

    if( argc!=2 )
    {
	cout << "Sposób użycia programu: interpreter kod" << endl;
	return -1;
    }

    cout << "Czytanie pliku " << argv[1] << endl;
    cout << "Before ifstream" << endl;
    ifstream plik( argv[1] );
    //ifstream plik( "dzielenie_c" );
    if( !plik )
    {
	cout << "Błąd: Nie można otworzyć pliku " << argv[1] << endl;
	return -1;
    }
    while( !plik.eof() )
    {
	plik >> com;
	i1 = ERROR;
        i2 = 0;
        i3 = 0;

    if( com=="REM" ) { i1 = REM; plik >> comment; }
	if( com=="GET" ) { i1 = GET; plik >> i2; }
	if( com=="PUT" ) { i1 = PUT; plik >> i2; }

	if( com=="LOAD"  ) { i1 = LOAD; plik >> i2; }
	if( com=="STORE" ) { i1 = STORE; plik >> i2; }

	if( com=="COPY" ) { i1 = COPY; plik >> i2; }
	if( com=="ADD"  ) { i1 = ADD; plik >> i2; }
	if( com=="SUB"  ) { i1 = SUB; plik >> i2; }
	if( com=="SHR"  ) { i1 = SHR; plik >> i2; }
	if( com=="SHL"  ) { i1 = SHL; plik >> i2; }
	if( com=="INC"  ) { i1 = INC; plik >> i2; }
	if( com=="DEC"  ) { i1 = DEC; plik >> i2; }
	if( com=="ZERO" ) { i1 = ZERO; plik >> i2; }

        if( com=="JUMP"  ) { i1 = JUMP; plik >> i3; }
	if( com=="JZERO" ) { i1 = JZERO; plik >> i2;  plik >> i3; }
	if( com=="JODD"  ) { i1 = JODD; plik >> i2;  plik >> i3; }
	if( com=="HALT"  ) { i1 = HALT; }

	if( i1==ERROR ) { cout << "Błąd: Nieznana instrukcja w linii " << k << "." << endl; return -1; }
        if( i2>reg-1 ) { cout << "Błąd: zły rejestr w instrukcji w linii " << k << endl; return -1; }
        if( i2<0 ) { cout << "Błąd: Zły rejestr w instrukcji w linii " << k << endl; return -1; }
        if( i3<0 ) { cout << "Błąd: Zły adress w instrukcji w linii " << k << endl; return -1; }

	if( plik.good() )
	{
	    program.push_back( make_tuple(i1,i2,i3) );
	}
	k++;
    }
    plik.close();
    cout << "Skończono czytanie pliku (" << program.size() << " linii)." << endl;

    cout << "Uruchamianie programu." << endl;
    lr = 0;
    srand(time(NULL));
    for(int i = 0; i<reg; i++ ) r[i] = rand();
    i = 0;
    while( get<0>(program[lr])!=HALT )	// HALT
    {
	switch( get<0>(program[lr]) )
	{
	    case REM:   cout << "Comment: " << comment << endl; ++lr; break;
        case GET:	cout << "? "; cin >> r[get<1>(program[lr])]; i+=100; lr++; break;
	    case PUT:	cout << "> " << r[get<1>(program[lr])] << endl; i+=100; lr++; break;

	    case LOAD:	DB("LOAD"); r[get<1>(program[lr])] = pam[r[0]]; i+=10; lr++; break;
	    case STORE:	DB("STORE"); pam[r[0]] = r[get<1>(program[lr])]; i+=10; lr++; break;

	    case ADD:   DB("ADD"); r[get<1>(program[lr])] += pam[r[0]] ; i+=10; lr++; break;
	    case SUB:   DB("SUB");
                        if( r[get<1>(program[lr])] >= pam[r[0]] )
                          r[get<1>(program[lr])] -= pam[r[0]];
                        else
                          r[get<1>(program[lr])] = 0;
                        i+=10; lr++; break;
	    case COPY:	r[0] = r[get<1>(program[lr])] ; i+=1; lr++; break;
	    case SHR:   DB("SHR"); r[get<1>(program[lr])] >>= 1; i+=1; lr++; break;
	    case SHL:   DB("SHL"); r[get<1>(program[lr])] <<= 1; i+=1; lr++; break;
	    case INC:   DB("INC"); r[get<1>(program[lr])]++ ; i+=1; lr++; break;
	    case DEC:   DB("DEC"); if( r[get<1>(program[lr])]>0 ) r[get<1>(program[lr])]--; i+=1; lr++; break;
	    case ZERO:  DB("ZERO"); r[get<1>(program[lr])] = 0; i+=1; lr++; break;

	    case JUMP: 	DB2("JUMP", get<2>(program[lr]), 0); lr = get<2>(program[lr]); i+=1; break;
	    case JZERO:	DB2("JZERO", get<1>(program[lr]), get<2>(program[lr]));
                    if( r[get<1>(program[lr])]==0 ) lr = get<2>(program[lr]); else lr++; i+=1; break;
	    case JODD:	DB2("JODD", get<1>(program[lr]), get<2>(program[lr]));
                    if( r[get<1>(program[lr])] % 2 != 0 ) lr = get<2>(program[lr]); else lr++; i+=1; break;
	    default: break;
	}
	DBREG;
	MEMPEEK;
	if( lr<0 || lr>=(int)program.size() )
	{
	    cout << "Błąd: Wywołanie nieistniejącej instrukcji nr " << lr << "." << endl;
	    return -1;
	}
    }
    cout << "Skończono program (czas: " << i << ")." << endl;

    return 0;
}
