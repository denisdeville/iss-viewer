export class TimeUtils {
    private static currentTimeInMs = (new Date()).getTime();

    private static intervalInSeconds = 10;

    public static getNextTimestamps(howMany: number) {
        const nextTimestamps = [];

        for(let i = 0; i < howMany; i++) {
            this.substractSeconds(this.intervalInSeconds);
            nextTimestamps.push(Math.round(this.currentTimeInMs / 1000));
        }

        return nextTimestamps;
    }

    private static substractSeconds(secondsToSubstract: number) {
        this.currentTimeInMs -= secondsToSubstract * 1000;
    }

    public static resetCurrentTime(): void {
        this.currentTimeInMs = (new Date()).getTime();
    }
}