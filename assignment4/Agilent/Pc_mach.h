#ifndef PC_MACH
#define PC_MACH
/***********************************************************************
*
* $Alterado para C++Builder 3.0
//$Auhor: Paulo Fernando Pereira de Carvalho
//$Laboratório de Percepção Computacional
//$Grupo de Automação e Controlo - Centro de Informática e Sistemas da Universidade de Coimbra
//$Departamento de Engenharia informática da Universidade de Coimbra
//$Date: 28-Maio-2002
//Revision: 1.0
/***********************************************************************
* $Author: stka $
* $Date: 2000/05/26 13:46:37 $
* $Revision: 2.0 $
* $Source: /mnt5/users/stka/mecif_lib/sources/RCS/pc_mach.h,v $
*
************************************************************************
*
* CONTENTS: pc_mach.h - type definitions for portable C
*
************************************************************************


                (C) Copyright Agilent Technologies, Inc. 2000

                            All rights reserved


    Agilent Technologies makes no warranty of any kind with regard to
    this software, including, but not limited to, the implied warranties
    of merchantability and fitness for a particular purpose.  Agilent
    Technologies shall not be liable for errors contained herein or
    direct, indirect, special, incidental or consequential damages in
    connection with the furnishing, performance or use of this material.

*/




typedef unsigned char   u_8;
//typedef int             i_16;
typedef short           i_16;
//typedef unsigned int    u_16;
typedef unsigned short  u_16;
//typedef long int        i_32;
typedef int             i_32;
//typedef unsigned long   u_32;
typedef unsigned int   u_32;
typedef float           f_32;
typedef double          f_64;
typedef unsigned char   b_8;
//typedef unsigned int    b_16;
typedef unsigned short    b_16;
typedef char            c_8;
//typedef unsigned int    c_16;
typedef unsigned short  c_16;
//typedef int             boolean;
//typedef short           boolean;
//typedef int             metachar;
typedef short             metachar;
typedef char            anydata;




/* Agilent CMS standard includes                                      */

#define  YES     1
#define  NO      0

#define  TRUE    1
#define  FALSE   0

#define  SUCCEED 1
#define  FAIL    0

#define  ON      1
#define  OFF     0

#define  UP      1
#define  DOWN    0

#define  LEFT    1
#define  RIGHT   0

#define  ACTIVE      1
#define  INACTIVE    0

#define HIGHBYTE(data)     (((data) >> 8) & 0xff)
#define LOWBYTE(data)      ((data) & 0xff)

#ifndef NULL
#define NULL    0L
#endif

#define PASSED   0

#define BLANK    0x20   /* define space character */
#define EOS      '\0'   /* define 'end of string' */

#endif
