import { hrtime } from 'process';

class ListItem {
    constructor(id, selected, title, url) {
        this.id = id;
        this.selected = selected;
        this.title = title;
        this.url = url;
    }
}

class Aor {
    constructor(size) {
        this.items = [];
        this.size = size;
    }

    fill() {
        for(let i=0; i<this.size; ++i) {
           let id = Math.trunc(Math.random() * (1<<30));
           this.items[i] = new ListItem(
               id,
               Math.random() < 0.5,
               `Item ${id}`,
               `http://item.dev/${id}`
               );
        }
    }

    randomIds() {
        let lids = this.items.map(item => item.id);
        let rids = [];
        for(let i=0; i<this.size; ++i) {
            let idx = Math.trunc(Math.random() * this.size);
            rids[i] = lids[idx];
        }
        return rids;
    }

    selected(id) {
        let item = this.items.find(item => item.id === id);
        return (item === undefined) ? false : item.selected;
    }
}

class Soa {
    constructor(size) {
        this.size = size;
        this.ids = new Int32Array(size);
        this.selecteds = new Uint8Array(size);
        this.titles = [];
        this.urls = [];
    }

    selected(id) {
        let idx = this.ids.indexOf(id);
        return this.selecteds[idx];
    }

    static fromAor(aor) {
        let soa = new Soa(aor.size);
        for(let i=0; i<aor.size; ++i) {
            let item = aor.items[i];
            soa.ids[i] = item.id;
            soa.selecteds[i] = item.selected;
            soa.titles[i] = item.title;
            soa.urls[i] = item.url;
        }
        return soa;
    }
}

class Mor {
    constructor(size) {
        this.size = size;
        this.items = new Map();
    }

    selected(id) {
        return this.items.get(id).selected;
    }

    static fromAor(aor) {
        let mor = new Mor(aor.size);
        aor.items.forEach(item => mor.items.set(item.id, item));
        return mor;
    }
}


function main() {
    let aor = new Aor(300);
    aor.fill();

    let soa = Soa.fromAor(aor);
    let mor = Mor.fromAor(aor);

    let sourceIds = aor.randomIds();
    let benchmarkIterations = 1000

    let aorBestTime = 1<<30;
    let soaBestTime = 1<<30;
    let morBestTime = 1<<30;

    let aorCount = 0;
    let soaCount = 0;
    let morCount = 0;

    console.log("Layout\tDuration (ns)\tCount")

    for(let j=0; j<benchmarkIterations; ++j) {
        const start = hrtime.bigint();
        sourceIds.forEach(id => {
            if (aor.selected(id)) {
                aorCount+=1;
            }
        });
        const duration = hrtime.bigint() - start;
        if (duration < aorBestTime) {
            aorBestTime = duration;
        }
    }

    console.log(`aor\t${aorBestTime}\t${aorCount}`);

    for(let j=0; j<benchmarkIterations; ++j) {
        const start = hrtime.bigint();
        sourceIds.forEach(id => {
            if (soa.selected(id)) {
                soaCount+=1;
            }
        });
        const duration = hrtime.bigint() - start;
        if (duration < soaBestTime) {
            soaBestTime = duration;
        }
    }

    console.log(`soa\t${soaBestTime}\t${soaCount}`);

    for(let j=0; j<benchmarkIterations; ++j) {
        const start = hrtime.bigint();
        sourceIds.forEach(id => {
            if (mor.selected(id)) {
                morCount++;
            }
        });
        const duration = hrtime.bigint() - start;
        if (duration < morBestTime) {
            morBestTime = duration;
        }
    }

    console.log(`mor\t${morBestTime}\t${morCount}`);
}

main();