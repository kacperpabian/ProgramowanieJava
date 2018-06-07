var move1 = function(arr) {

    var s = arr.length;
    var Rand = Java.type('game.Rand');
    var r = new Rand();

    var m1 = r.lengthRandom(s);
    var m2 = r.lengthRandom(s);
    var buf1 = m1;
    var buf2 = m2;

    //sprawdzam czy zajete
    while(arr[m1][m2] !== 0){
        m1++;
        if(m1 === s){
            m1 = 0;
            m2++;
            if(m2 === s){
                m2 = 0;
            }
        }
        if (m1 === buf1 && m2 === buf2){
            return -1 + " ";
        }
    }
    return m1 + " " + m2;
};

var move2 = function(arr) {

    var s = arr.length;
    var Rand = Java.type('game.Rand');
    var r = new Rand();

    var m1 = r.lengthRandom(s);
    var m2 = r.lengthRandom(s);
    var m1Stable = m1;
    var m2Stable = m2;
    var buf1;
    var buf2;

    var x = false;

    while (x === false)
    {
        var noX = false;

        buf1 = m1Stable;
        buf2 = m2Stable;

        while(arr[m1][m2] !== 1 && noX === false){
            m1++;
            if(m1 === s){
                m1 = 0;
                m2++;
                if(m2 === s){
                    m2 = 0;
                }
            }
            if (m1 === buf1 && m2 === buf2){
                noX = true;
            }
        }
        buf1 = m1;
        buf2 = m2;

        while(arr[buf1][buf2] !== 0){
            rMove = r.lengthRandom(8);

            switch(rMove)
            {
                case 0:
                    buf1++;
                    if(buf1 === s) {
                        buf1 = buf1 - 2;
                    }
                    break;
                case 1:
                    buf2++;
                    if(buf2 === s){
                        buf2 = buf2 - 2;
                    }
                    break;
                case 2:
                    buf1++;
                    buf2++;
                    if(buf1 === s) {
                        buf1 = buf1 - 1;
                    }
                    if(buf2 === s){
                        buf2 = 1;
                    }
                    break;
                case 3:
                    buf1--;
                    buf2++;
                    if(buf1 === -1) {
                        buf1 = buf1 + 1;
                    }
                    if(buf2 === s){
                        buf2 = 1;
                    }
                    break;
                case 4:
                    buf1++;
                    buf2--;
                    if(buf1 === s) {
                        buf1 = 1;
                    }
                    if(buf2 === -1){
                        buf2 = buf2 + 1;
                    }
                    break;
                case 5:
                    buf1--;
                    buf2--;
                    if(buf1 === -1) {
                        buf1 = buf1 + 1;
                    }
                    if(buf2 === -1){
                        buf2 = buf2 + 1;
                    }
                    break;
                case 6:
                    buf1--;
                    if(buf1 === -1) {
                        buf1 = buf1 + 2;
                    }
                    break;
                case 7:
                    buf2--;
                    if(buf2 === -1) {
                        buf2 = buf2 + 2;
                    }
                    break;
            }

        }

        x = true;

    }

    return buf1 + " " + buf2;

};
