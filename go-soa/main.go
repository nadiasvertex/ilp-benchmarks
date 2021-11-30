package main

import (
	"fmt"
	"math/rand"
	"time"
)

type ListItem struct {
	Id int32
	Selected bool
	Title string
	Url string
}

type Selector interface {
	Selected(id int32) bool
}

type Aos struct {
	items []ListItem
}

type Soa struct {
	ids []int32
	selected []bool
}

type Mor struct {
	ids map[int32]ListItem
}

func (aos *Aos) fill() {
	for i := range aos.items {
		item := &aos.items[i]
		item.Id = rand.Int31()
		item.Selected = rand.Intn(2) == 0
		item.Title = fmt.Sprintf("Item %d", item.Id)
		item.Url = fmt.Sprintf("http://item.dev/%d", item.Id)
	}
}

func (aos *Aos) randomIds() []int32 {
	size := len(aos.items)
	lids := make([]int32, size)
	rids := make([]int32, size)
	for i := range aos.items {
		lids[i] = aos.items[i].Id
	}
	for i := 0; i<size; i++ {
		rids[i] = lids[rand.Intn(size)]
	}
	return rids
}

func (aos *Aos) Selected(id int32) bool {
	for i := range aos.items {
		item := &aos.items[i]
		if item.Id == id {
			return item.Selected
		}
	}
	return false
}

func (soa *Soa) Selected(id int32) bool {
	for i:= range soa.ids {
		if soa.ids[i] == id {
			return soa.selected[i]
		}
	}

	return false
}

func (mor *Mor) Selected(id int32) bool {
	return mor.ids[id].Selected
}

func NewAos(size int) *Aos {
	aos := &Aos{
		items: make([]ListItem, size),
	}
	aos.fill()
	return aos
}

func NewSoa(aos *Aos) *Soa {
	soa := &Soa{ids: make([]int32, len(aos.items)), selected: make([]bool, len(aos.items))}
	for i := range aos.items {
		soa.ids[i] = aos.items[i].Id
		soa.selected[i] = aos.items[i].Selected
	}
	return soa
}

func NewMor(aos *Aos) *Mor {
	mor := &Mor{ids: make(map[int32]ListItem)}
	for i := range aos.items {
		mor.ids[aos.items[i].Id] = aos.items[i]
	}
	return mor
}

func main() {
	aos := NewAos(300)
	soa := NewSoa(aos)
	mor := NewMor(aos)
	sourceIds := aos.randomIds()

	benchmarkIterations := 1000

	var aosBestTime time.Duration = 1<<30
	var soaBestTime time.Duration = 1<<30
	var morBestTime time.Duration = 1<<30

	aosCount := 0
	soaCount := 0
	morCount := 0

	fmt.Printf("Layout\tDuration (ns)\tCount\n")

	for j:=0; j <benchmarkIterations; j++ {
		start := time.Now()
		for _, id := range sourceIds {
			if aos.Selected(id) {
				aosCount++
			}
		}
		duration := time.Since(start)
		if duration < aosBestTime {
			aosBestTime = duration
		}
	}

	fmt.Printf("aos\t%d\t%d\n", aosBestTime.Nanoseconds(), aosCount)

	for j:=0; j <benchmarkIterations; j++ {
		start := time.Now()
		for _, id := range sourceIds {
			if soa.Selected(id) {
				soaCount++
			}
		}
		duration := time.Since(start)
		if duration < soaBestTime {
			soaBestTime = duration
		}
	}

	fmt.Printf("soa\t%d\t%d\n", soaBestTime.Nanoseconds(), soaCount)

	for j:=0; j <benchmarkIterations; j++ {
		start := time.Now()
		for _, id := range sourceIds {
			if mor.Selected(id) {
				morCount++
			}
		}
		duration := time.Since(start)
		if duration < morBestTime {
			morBestTime = duration
		}
	}

	fmt.Printf("mor\t%d\t%d\n", morBestTime.Nanoseconds(), morCount)
}