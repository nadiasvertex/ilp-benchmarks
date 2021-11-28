#include <cstdint>
#include <algorithm>
#include <chrono>
#include <iostream>
#include <random>

struct list_item {
    int id;
    bool selected;
};

struct aos {
    explicit aos(std::size_t _size) : size(_size), items(_size) {
        fill();
    }

    void fill() {
        std::random_device r;
        std::default_random_engine e1(r());
        std::uniform_int_distribution<int> uniform_dist(1, size);
        std::transform(begin(items), end(items), begin(items), [&uniform_dist, &e1](auto &item) {
            item.id = uniform_dist(e1);
            item.selected = (uniform_dist(e1) % 10) > 5;
            return item;
        });
    }

    [[nodiscard]] auto random_ids() const -> std::vector<int>  {
        std::random_device r;
        std::default_random_engine e1(r());
        std::uniform_int_distribution<int> uniform_dist(1, size);
        std::vector<int> rids(size);
        for(auto i=0; i<size; ++i) {
            rids[i] = uniform_dist(e1);
        }
        return rids;
    }

    [[nodiscard]] bool selected(int id) const {
        for (const auto &item : items) {
            if (item.id == id) {
                return item.selected;
            }
        }
        return false;
    }

    std::size_t size;
    std::vector<list_item> items;
};

struct soa {
    explicit soa(const aos &src) : ids_(src.size), selected_(src.size) {
        for (auto i = 0; i < src.size; ++i) {
            const auto &item = src.items[i];
            ids_[i] = item.id;
            selected_[i] = item.selected;
        }
    }

    [[nodiscard]] bool selected(int id) const {
        for(auto i=0; i< ids_.size(); i++) {
            if(ids_[i]==id) {
                return selected_[i];
            }
        }
        return false;
    }

    std::vector<int> ids_;
    std::vector<bool> selected_;
};

int main() {
    using namespace std::chrono_literals;
    aos aos_o(300);
    soa soa_o(aos_o);

    int benchmark_iterations = 100000;

    auto item_ids = aos_o.random_ids();
    std::chrono::nanoseconds soa_best_duration = 24h, aos_best_duration = 24h;
    std::uint64_t soa_count=0, aos_count=0;

    std::cout << "Layout\tDuration (ns)\tCount\n";

    for(auto j=0; j<benchmark_iterations; ++j) {
        auto start_time = std::chrono::steady_clock::now();
        for(auto id : item_ids) {
            if (aos_o.selected(id)) {
                ++aos_count;
            }
        }
        auto end_time = std::chrono::steady_clock::now();
        auto duration = end_time - start_time;
        if (duration < aos_best_duration) {
            aos_best_duration = duration;
        }
    }

    std::cout << "aos\t" << aos_best_duration.count() << "\t" << aos_count << "\n";

    for(auto j=0; j<benchmark_iterations; ++j) {
        auto start_time = std::chrono::steady_clock::now();
        for(auto id : item_ids) {
            if (soa_o.selected(id)) {
                ++soa_count;
            }
        }
        auto end_time = std::chrono::steady_clock::now();
        auto duration = end_time - start_time;
        if (duration < soa_best_duration) {
            soa_best_duration = duration;
        }
    }

    std::cout << "soa\t" << soa_best_duration.count() << "\t" << soa_count << "\n";

    return 0;
}
