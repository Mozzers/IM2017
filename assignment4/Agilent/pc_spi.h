#ifndef PC_SPI_H
#define PC_SPI_H
/***********************************************************************
*
* $Author: stka $
* $Date: 2000/05/26 13:45:38 $
* $Revision: 2.0 $
* $Source: /mnt5/users/stka/mecif_lib/sources/RCS/pc_spi.h,v $
*
************************************************************************
*
* CONTENTS: pc_spi.h - contains all defines and typedefs which are
*                      required for processing messages of the Agilent
*                      CMS Standard Parameter Interface (SPI)
*
* CAUTION: This is a PC adapted MECIF version of the file 'spi.h'.
*          In all type definitions where two successive 8-bit elements
*          occur these are swapped.
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




/*-SYSTEM INCLUDES----------------------------------------------------*/

#include "pc_mach.h"


/*-DEFINES------------------------------------------------------------*/

/*------------------- some character string lengths ------------------*/

#define SPI_ALERT_LENGTH        19
#define SPI_LABEL_LENGTH        7
#define SPI_RT_UNIT_LENGTH      5
#define SPI_NRM_UNIT_LENGTH     (9+1)
#define SPI_UNIT_LENGTH         19
#define SPI_NUM_STRING_LENGTH   21
#define SPI_ENUM_LENGTH         (9 + 1)
#define CDD_DEV_NAME_LENGTH     (19 + 1)


/*---------------------- record identifiers --------------------------*/

#define SPI_ABS_TIME_STAMP      1
#define SPI_ALARM_LIMITS        3
#define SPI_CALIBR_PARAM        11
#define SPI_CW_FIXED            12
#define SPI_GAIN_OFFSET         15
#define SPI_NUMERIC             17
#define SPI_NUMERIC_STRING      18
#define SPI_NU_FIXED            19
#define SPI_RANGE               24
#define SPI_UNIT                28
#define SPI_WS_FIXED            29
#define SPI_RT_UNIT             35
#define SPI_ENUM                42
#define SPI_NRM_UNIT            47

#define SPI_CDD_NU_FIXED                128        /* Cdd of MIP-ASW  */
#define SPI_CDD_SET_FIXED               129        /* Cdd of MIP-ASW  */
#define SPI_CDD_DEV_NAME_FIXED          130        /* Cdd of MIP-ASW  */
#define SPI_CDD_TRANSPARENT_FIXED       131        /* Cdd of MIP-ASW  */
#define SPI_CDD_SYNC                    132        /* Cdd of MIP-ASW  */
#define SPI_CDD_EMPTY_MSG               255        /* Cdd of MIP-ASW  */


/*---------------------- some special values -------------------------*/

#define SpiNaN1                0x007f
#define SpiNaN2                0xffff
#define SpiNaN_32              0x007fffff

#define SPI_INV_LEVEL          0xffff

#define SPI_MAX_NUMERICS       3      /* number of spi numerics per messge */




/*-TYPEDEFS-----------------------------------------------------------*/

typedef struct
    {
    b_16        mesg_header;
    u_8         mesg_length;
    u_8         rec_size;
    u_16        sample[1];
    } SpiCwFixed1;

typedef struct
    {
    b_16        mesg_header;
    u_8         mesg_length;
    u_8         rec_size;
    u_16        sample[4];
    } SpiCwFixed4;

typedef struct
    {
    b_16        mesg_header;
    u_8         mesg_length;
    u_8         rec_size;
    u_16        sample[16];
    } SpiCwFixed16;

typedef struct
    {
    b_16        mesg_header;
    u_8         mesg_length;
    u_8         rec_size;
    u_16        sample[64];
    } SpiCwFixed64;

typedef struct
    {
    b_16        mesg_header;
    u_8         mesg_length;
    u_8         rec_size;
    b_16        psw;
    u_16        number_of_samples;
    b_16        flags;
    b_16        label_code;
    c_16        label_string[SPI_LABEL_LENGTH];
    } SpiWsFixed;

typedef struct
    {
    u_8         rec_type;
    u_8         rec_size;
    u_16        gain_1;
    u_16        gain_2;
    u_16        offset_1;
    u_16        offset_2;
    } SpiGainOffset;

typedef struct
    {
    u_8         rec_type;
    u_8         rec_size;
    u_16        lower_mark;
    u_16        lower_cal_1;
    u_16        lower_cal_2;
    u_16        upper_mark;
    u_16        upper_cal_1;
    u_16        upper_cal_2;
    } SpiCalibrParam;

typedef struct
    {
    u_8         rec_type;
    u_8         rec_size;
    b_16        flags;
    b_16        unit_code;
    c_16        unit_string[SPI_UNIT_LENGTH];
    } SpiUnit;

typedef struct
    {
    u_8         rec_type;
    u_8         rec_size;
    b_16        flags;
    b_16        unit_code;
    c_16        unit_string[SPI_RT_UNIT_LENGTH];
    } SpiRtUnit;

typedef struct
    {
    u_8         rec_type;
    u_8         rec_size;
    b_16        flags;
    b_16        unit_code;
    c_16        unit_string[SPI_NRM_UNIT_LENGTH];
    } SpiNrmUnit;

typedef struct
    {
    b_16        mesg_header;
    u_8         mesg_length;
    u_8         rec_size;
    b_16        psw;
    b_16        flags;
    b_16        label_code;
    c_16        label_string[SPI_LABEL_LENGTH];
    } SpiNuFixed;

typedef struct
    {
    u_8         rec_type;
    u_8         rec_size;
    b_16        label_code;
    u_16        value_1;
    u_16        value_2;
    } SpiNumeric;

typedef struct
    {
    u_8         rec_type;
    u_8         rec_size;
    b_16        label_code;
    u_16        lower_limit_1;
    u_16        lower_limit_2;
    u_16        upper_limit_1;
    u_16        upper_limit_2;
    } SpiAlarmLimits;

typedef struct
    {
    u_8         rec_type;
    u_8         rec_size;
    u_16        range_min_1;
    u_16        range_min_2;
    u_16        range_max_1;
    u_16        range_max_2;
    } SpiRange;

typedef struct
    {
    u_8         rec_type;
    u_8         rec_size;
    b_16        year_month;
    b_16        day_hour;
    b_16        minute_second;
    } SpiAbsTimeStamp;

typedef struct
    {
    u_8         rec_type;
    u_8         rec_size;
    c_16        string[SPI_NUM_STRING_LENGTH];
    } SpiNumericString;


/*****************************************************************************/
/* Typedefs for MIP CDD (Comprehensive Device Data)                        */

typedef struct
    {
    u_16         mesg_header;          /* header of the message              */
    u_8          mesg_length;          /* total length of the message        */
    u_8          unused;
    u_8          rec_type;
    u_8          rec_size;
    b_16         validity;             /* validity as defined in psw_macro.h */
    b_16         flags;                /* analog num_flags as in SpiNuFixed  */
    b_16         label_code;
    c_16         label_string[SPI_LABEL_LENGTH];
    } SpiCddNuFixed;

typedef struct
    {
    u_16         mesg_header;          /* header of the message              */
    u_8          mesg_length;          /* total length of the message        */
    u_8          unused;
    u_8          rec_type;
    u_8          rec_size;
    b_16         label_code;
    c_16         label_string[SPI_LABEL_LENGTH];
    } SpiCddSetFixed;

typedef struct
    {
    u_16         mesg_header;          /* header of the message              */
    u_8          mesg_length;          /* total length of the message        */
    u_8          unused;
    u_8          rec_type;
    u_8          rec_size;
    c_16         dev_name[CDD_DEV_NAME_LENGTH];
    } SpiCddDevNameFixed;

typedef struct
    {
    u_16         mesg_header;          /* header of the message              */
    u_8          mesg_length;          /* total length of the message        */
    u_8          unused;
    u_8          rec_type;
    u_8          rec_size;
    u_16         data_word[255];
    } SpiCddTransparent;

typedef struct
    {
    u_8          rec_type;
    u_8          rec_size;
    u_16         enum_code;
    c_16         enum_string[SPI_ENUM_LENGTH];
    } SpiCddEnum;

typedef struct
    {
    u_8          rec_type;
    u_8          rec_size;
    u_16         dummy;
    } SpiCddSync;

typedef struct
    {
    u_16         mesg_header;          /* header of the message              */
    u_8          mesg_length;          /* total length of the message        */
    u_8          unused;
    u_8          rec_type;
    u_8          rec_size;
    u_16         dummy;
    } SpiCddEmptyMsg;

#endif

