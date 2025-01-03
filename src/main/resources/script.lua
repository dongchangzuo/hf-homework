local key = KEYS[1]
local threshold = tonumber(ARGV[1])

local currentTime = redis.call('TIME')
local currentTimestamp = tonumber(currentTime[1]) * 1000 + math.floor(tonumber(currentTime[2]) / 1000)

redis.call('RPUSH', key, currentTimestamp)

while true do
    local oldestTimestamp = redis.call('LRANGE', key, 0, 0)[1]
    if not oldestTimestamp then
        break
    end

    oldestTimestamp = tonumber(oldestTimestamp)
    if currentTimestamp - oldestTimestamp > threshold then
        redis.call('LPOP', key)
    else
        break
    end
end

return redis.call('LLEN', key)
